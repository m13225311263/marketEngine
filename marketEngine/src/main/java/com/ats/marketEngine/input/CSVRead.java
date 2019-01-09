package com.ats.marketEngine.input;

import com.ats.marketEngine.entity.Order;
import com.csvreader.CsvReader;
import java.io.IOException;
import java.util.ArrayList;
import lombok.Data;

/**
 * Creator:guxt Date 2019/01/09
 */


@Data
public class CSVRead {
    private String filePath;
    private ArrayList<Order> autoOrders;

    public CSVRead(String filepath){
        this.filePath = filepath;
        this.autoOrders = new ArrayList<Order>(10000);
        readCsvFild();
    }

    private void readCsvFild(){
        try {
            CsvReader csvReader = new CsvReader(this.filePath);
            csvReader.readHeaders();

            while (csvReader.readRecord()){
                int side = (int)(Float.parseFloat(csvReader.get("Side")));
                int price = (int)(Float.parseFloat(csvReader.get("Price"))*100);
                int qty = Integer.parseInt(csvReader.get("OrderQty"));
                Order order = new Order(0,1,side,price,qty);
                autoOrders.add(order);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
