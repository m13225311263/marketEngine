package com.ats.marketEngine.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Creator:guxt Date 2019/01/08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private int marketID;
    private int traderID;
    private int side;
    private int price;//price*100
    private int qty;
}
