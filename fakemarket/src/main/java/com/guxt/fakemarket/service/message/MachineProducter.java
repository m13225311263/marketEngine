package com.guxt.fakemarket.service.message;

import com.guxt.fakemarket.entity.Order;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Creator:guxt Date 2019/01/11
 */

@Service
public class MachineProducter {

    @Async
    public void run(ConcurrentLinkedQueue<Order> orderConcurrentLinkedQueue,
        ArrayList<Order> autoOrders) {
        System.out.println("int Producter");

        Order order;
        Date currentTime;

        try {
            Iterator<Order> iterator = autoOrders.iterator();
            SimpleDateFormat df = new SimpleDateFormat("hhmmss");
            while (iterator.hasNext()) {
                currentTime = getCurrentTime();
                order = iterator.next();
                if (order != null) {
                    while (order.getOrderDate().compareTo(currentTime) > 0) {
                        currentTime = getCurrentTime();
                        System.out.println(df.format(currentTime));
                    }
                    orderConcurrentLinkedQueue.add(order);
                    System.out.println("OrderTime:"+df.format(order.getOrderDate())+"CurrentTime:"+df.format(currentTime));

                } else {
                    System.out.println("Has a null order !!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private  Date getCurrentTime(){
        Calendar cal = Calendar.getInstance();
        return cal.getTime();

    }
}
