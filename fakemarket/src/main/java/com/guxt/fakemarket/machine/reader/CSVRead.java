package com.guxt.fakemarket.machine.reader;

import com.csvreader.CsvReader;
import com.guxt.fakemarket.entity.Order;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;

/**
 * Creator:guxt Date 2019/01/09
 * Update 2019/01/11
 */


@Data
public class CSVRead extends MachineDataRead {

    private String relativePath;
    private int beginPrice = 0;

    public CSVRead(String relativePath) {
        this.relativePath = relativePath;
    }

    @Override
    public void loadData() {
        String dirPath = System.getProperty("user.dir");
        String filePath = dirPath.concat(relativePath);
        try {
            CsvReader csvReader = new CsvReader(filePath);
            csvReader.readHeaders();

            while (csvReader.readRecord()) {

                int side = (int) (Float.parseFloat(csvReader.get("Side")));
                int price = (int) (Float.parseFloat(csvReader.get("Price")) * 100);
                int qty = Integer.parseInt(csvReader.get("OrderQty"));
                SimpleDateFormat df = new SimpleDateFormat("hhmmss");
                String time = csvReader.get("RecvTime").substring(8,14);
                Date date_order = df.parse(time);
                if(beginPrice == 0){
                    beginPrice = price;
                }
                Order order = new Order(0, 1, side, price, qty,date_order);
                autoOrders.add(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}