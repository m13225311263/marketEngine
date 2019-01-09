package com.ats.marketEngine.service;

import com.ats.marketEngine.entity.Order;
import com.ats.marketEngine.entity.OrderBookUnit;
import java.util.LinkedList;
import java.util.Vector;


/**
 * Creator:guxt Date 2019/01/08
 */


public class MarchEngine {


    private Vector<LinkedList<OrderBookUnit>> pricePoint;//采用并行化的会好
    private Vector<OrderBookUnit> orderBook;
    private int askMin;//之后放入配置文件
    private int bidMax;
    private int currentID;//考虑使用atomic

    public MarchEngine() {
        int pricePointCapacity = 70001;
        this.pricePoint = new Vector<LinkedList<OrderBookUnit>>(pricePointCapacity);
        this.orderBook = new Vector<OrderBookUnit>(pricePointCapacity);
        for (int i = 0; i < pricePointCapacity; i++) {
            LinkedList<OrderBookUnit> linkedList = new LinkedList<OrderBookUnit>();
            pricePoint.add(linkedList);
            OrderBookUnit orderBookUnit = new OrderBookUnit();
            orderBook.add(orderBookUnit);
        }
        this.bidMax = 70000;// 改掉
        this.askMin = 1;//改掉
        this.currentID = 0;

    }

    public void executeTrade(int marketID, int traderID, int traderID2, int price, int qty) {

        System.out.println(
            "| MarketID: " + marketID + "|TraderID: " + traderID + "|TraderID2: " + traderID2
                + "|Price " + price + "|Qty: " + qty);

    }

    public int placeAOrder(Order order) {
        int orderPrice = order.getPrice();
        int orderQty = order.getQty();
        if (order.getSide() == 0) {
            //buy
            if (orderPrice >= this.askMin) {
                LinkedList<OrderBookUnit> oBList = pricePoint.elementAt(askMin);
                do {
                    for (OrderBookUnit obListEntry : oBList) {
                        if (obListEntry.getQty() < orderQty) {
                            executeTrade(order.getMarketID(), order.getTraderID(),
                                obListEntry.getTraderID(), orderPrice,
                                obListEntry.getQty());
                            orderQty -= obListEntry.getQty();
                            oBList.poll();
                        } else {
                            executeTrade(order.getMarketID(), order.getTraderID(),
                                obListEntry.getTraderID(), orderPrice,
                                orderQty);
                            if (obListEntry.getQty() > orderQty) {
                                obListEntry.setQty(obListEntry.getQty() - orderQty);
                            } else {
                                oBList.poll();
                            }
                            return ++currentID;
                        }
                    }
                    askMin++;
                } while (orderPrice >= askMin);
            }
            OrderBookUnit orderBookUnit = orderBook.elementAt(++currentID);
            orderBookUnit.setQty(orderQty);
            orderBookUnit.setTraderID(order.getTraderID());
            pricePoint.elementAt(orderPrice).add(orderBookUnit);
            if (bidMax < orderPrice) {
                bidMax = orderPrice;
            }
            return currentID;
        } else if (order.getSide() == 1) {
            //sell
            if (orderPrice <= this.bidMax) {
                LinkedList<OrderBookUnit> oBList = pricePoint.elementAt(bidMax);
                do {
                    for (OrderBookUnit obListEntry : oBList) {
                        if (obListEntry.getQty() < orderQty) {
                            executeTrade(order.getMarketID(), order.getTraderID(),
                                obListEntry.getTraderID(), orderPrice,
                                obListEntry.getQty());
                            orderQty -= obListEntry.getQty();
                            oBList.poll();
                        } else {
                            executeTrade(order.getMarketID(), order.getTraderID(),
                                obListEntry.getTraderID(), orderPrice,
                                orderQty);
                            if (obListEntry.getQty() > orderQty) {
                                obListEntry.setQty(obListEntry.getQty() - orderQty);
                            } else {
                                oBList.poll();
                            }
                            return ++currentID;
                        }
                    }
                    bidMax--;
                } while (orderPrice <= bidMax);
            }
            OrderBookUnit orderBookUnit = orderBook.elementAt(++currentID);
            orderBookUnit.setQty(orderQty);
            orderBookUnit.setTraderID(order.getTraderID());
            pricePoint.elementAt(orderPrice).add(orderBookUnit);
            if (askMin > orderPrice) {
                askMin = orderPrice;
            }
            return currentID;

        } else {
            System.out.println("Wrong order side!!!");
            return 0;

        }

    }

    public static enum ME {
        INSTANCE;
        private MarchEngine marchEngine;

        ME() {
            marchEngine = new MarchEngine();
        }

        public MarchEngine getInstance() {
            return marchEngine;
        }
    }
}

