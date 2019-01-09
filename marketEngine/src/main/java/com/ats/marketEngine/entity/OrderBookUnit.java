package com.ats.marketEngine.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Creator:guxt Date 2019/01/08
 */
@Data
@NoArgsConstructor
public class OrderBookUnit {

    private int qty;
    private int traderID;
}
