package com.guxt.fakemarket.service.message;

import com.guxt.fakemarket.entity.Order;
import com.guxt.fakemarket.service.MarchEngine;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Creator:guxt Date 2019/01/11
 */
@Service
public class OrderCustomer {
    private volatile boolean isRunning = true;

    @Async
    public void run(ConcurrentLinkedQueue<Order> orderConcurrentLinkedQueue, MarchEngine me) {

        Order order;
        try{
            while (true){
                if(!orderConcurrentLinkedQueue.isEmpty()) {
                    order = orderConcurrentLinkedQueue.poll();
                    me.placeAOrder(order);
                    System.out.println("PlaceOrder:"+ order.toString());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void runStop(){
        isRunning = false;
    }
}
