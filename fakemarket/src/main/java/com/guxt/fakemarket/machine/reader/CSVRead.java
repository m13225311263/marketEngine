package com.guxt.fakemarket.machine.reader;

import com.csvreader.CsvReader;
import com.guxt.fakemarket.entity.Order;
import java.io.IOException;
import lombok.Data;

/**
 * Creator:guxt Date 2019/01/09
 */


@Data
public class CSVRead extends MachineDataRead {

    private String relativePath;

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
                Order order = new Order(0, 1, side, price, qty);
                autoOrders.add(order);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}