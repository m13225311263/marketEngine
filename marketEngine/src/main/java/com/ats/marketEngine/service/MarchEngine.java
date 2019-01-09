package com.ats.marketEngine.service;

import com.ats.marketEngine.entity.Order;
import com.ats.marketEngine.entity.OrderBookUnit;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;
import lombok.Getter;


/**
 * Creator:guxt Date 2019/01/08
 */


public class MarchEngine {


    private Vector<LinkedList<OrderBookUnit>> pricePoint;//采用并行化的会好
    private Vector<OrderBookUnit> orderBook;
    private int askMin;//之后放入配置文件
    private int bidMax;
    private int currentID;//考虑使用atomic
    @Getter
    private int countTime;//记录撮合次数

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

    public void initialize(int beginPrice) {//开盘价
        this.countTime = 0;
        this.bidMax = beginPrice+beginPrice/10+1;
        this.askMin = beginPrice-(beginPrice/10+1);
        this.currentID = 0;
        int orderbookitem = 100000;
        this.pricePoint = new Vector<LinkedList<OrderBookUnit>>(bidMax+1);
        this.orderBook = new Vector<OrderBookUnit>(orderbookitem);
        for (int i = 0; i < (bidMax+1); i++) {
            LinkedList<OrderBookUnit> linkedList = new LinkedList<OrderBookUnit>();
            pricePoint.add(linkedList);
        }
        for(int i = 0; i<orderbookitem;i++){
            OrderBookUnit orderBookUnit = new OrderBookUnit();
            orderBook.add(orderBookUnit);
        }
    }


    private void executeTrade(int marketID, int traderID, int traderID2, int price, int qty) {
        countTime++;

        System.out.println(
            "| MarketID: " + marketID + "|TraderID: " + traderID + "|TraderID2: " + traderID2
                + "|Price " + price + "|Qty: " + qty);

    }

    public int placeAOrder(Order order) {
        int orderPrice = order.getPrice();
        int orderQty = order.getQty();
        if (order.getSide() == 2) {
            //buy
            if (orderPrice >= this.askMin) {
                askMin = orderPrice - orderPrice/10;
                do {
                    LinkedList<OrderBookUnit> oBList = pricePoint.elementAt(askMin);
                    Iterator<OrderBookUnit> obIterator = oBList.iterator();
                    while (obIterator.hasNext()) {
                        OrderBookUnit obListEntry = obIterator.next();
                        if (obListEntry.getQty() < orderQty) {
                            executeTrade(order.getMarketID(), order.getTraderID(),
                                obListEntry.getTraderID(), orderPrice,
                                obListEntry.getQty());
                            orderQty -= obListEntry.getQty();
                            obIterator.remove();
                        } else {
                            executeTrade(order.getMarketID(), order.getTraderID(),
                                obListEntry.getTraderID(), orderPrice,
                                orderQty);
                            if (obListEntry.getQty() > orderQty) {
                                obListEntry.setQty(obListEntry.getQty() - orderQty);
                            } else {
                                obIterator.remove();
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
            return currentID;
        } else if (order.getSide() == 1) {
            //sell
            if (orderPrice <= this.bidMax) {
                bidMax = orderPrice+orderPrice/10;
                do {
                    LinkedList<OrderBookUnit> oBList = pricePoint.elementAt(askMin);
                    Iterator<OrderBookUnit> obIterator = oBList.iterator();
                    while (obIterator.hasNext()) {
                        OrderBookUnit obListEntry = obIterator.next();
                        if (obListEntry.getQty() < orderQty) {
                            executeTrade(order.getMarketID(), order.getTraderID(),
                                obListEntry.getTraderID(), orderPrice,
                                obListEntry.getQty());
                            orderQty -= obListEntry.getQty();
                            obIterator.remove();
                        } else {
                            executeTrade(order.getMarketID(), order.getTraderID(),
                                obListEntry.getTraderID(), orderPrice,
                                orderQty);
                            if (obListEntry.getQty() > orderQty) {
                                obListEntry.setQty(obListEntry.getQty() - orderQty);
                            } else {
                                obIterator.remove();
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
            return currentID;

        } else {
            System.out.println("Wrong order side!!!");
            return 0;

        }
    }

}

