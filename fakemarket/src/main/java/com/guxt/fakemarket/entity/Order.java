package com.guxt.fakemarket.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Creator:guxt Date 2019/01/08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component

public class Order {

    private int marketID;
    private int traderID;
    private int side;
    private int price;//price*100
    private int qty;
    private Date orderDate;
}
