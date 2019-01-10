package com.guxt.fakemarket.machine.reader;

import com.guxt.fakemarket.entity.Order;
import java.util.ArrayList;
import lombok.Getter;

/**
 * Creator:guxt Date 2019/01/10
 */


public class MachineDataRead {

    @Getter
    protected ArrayList<Order> autoOrders;

    public MachineDataRead() {
        this.autoOrders = new ArrayList<Order>(10000);
    }

    public void loadData() {
    }


}
