package com.guxt.fakemarket.service;

import com.guxt.fakemarket.entity.Order;
import com.guxt.fakemarket.machine.reader.CSVRead;
import com.guxt.fakemarket.machine.reader.MachineDataRead;
import com.guxt.fakemarket.service.message.MachineProducter;
import com.guxt.fakemarket.service.message.OrderCustomer;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


/**
 * Creator:guxt Date 2019/01/10
 */

@Component
@NoArgsConstructor
@Configuration
public class MarketPlugin {

    @Autowired
    MarchEngine me;
    @Autowired
    MachineProducter machineProducter;
    @Autowired
    OrderCustomer orderCustomer;
    @Value("${csvRead.relativePath}")
    private String relativePath;

    public void systest() {
        ConcurrentLinkedQueue<Order> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        MachineDataRead machineDataRead = new CSVRead(relativePath);
        machineDataRead.loadData();
        me.initialize(((CSVRead) machineDataRead).getBeginPrice());
        ArrayList<Order> autoOrders = machineDataRead.getAutoOrders();
        machineProducter.run(concurrentLinkedQueue,autoOrders);
        orderCustomer.run(concurrentLinkedQueue,me);

    }

   /* public void test() {
        int begin_price = 2449;
        me.initialize(begin_price);

        long startTime = System.currentTimeMillis();//获取开始时间
        for (Order order : autoOrders) {
            me.placeAOrder(order);
        }
        long endTime = System.currentTimeMillis(); //获取结束时间
        System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
        System.out.println(me.getCountTime());

    }*/


}
