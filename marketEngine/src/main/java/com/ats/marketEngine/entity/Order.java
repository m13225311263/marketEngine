package com.ats.marketEngine.entity;

import lombok.Data;

/**
 * Creator:guxt Date 2019/01/08
 */
@Data
public class Order {

    private int marketID;
    private int traderID;
    private int side;
    private int price;//price*100
    private int qty;
}
