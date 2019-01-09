import com.ats.marketEngine.entity.Order;
import com.ats.marketEngine.input.CSVRead;
import com.ats.marketEngine.service.MarchEngine;
import com.ats.marketEngine.service.MarchEngine.ME;
import java.util.ArrayList;

/**
 * Creator:guxt Date 2019/01/08
 */
public class main {

    public static void main(String[] args) {
        int begin_price = 2449 ;
        MarchEngine me = ME.INSTANCE.getInstance();
        me.initialize(begin_price);
        String dirPath = System.getProperty("user.dir");
        String filePath= dirPath+"\\src\\main\\resources\\test.csv";
        CSVRead csvRead = new CSVRead(filePath);
        ArrayList<Order> autoOrders =csvRead.getAutoOrders();
        long startTime=System.currentTimeMillis();//获取开始时间
        for(Order order: autoOrders){
            me.placeAOrder(order);
        }
        long endTime=System.currentTimeMillis(); //获取结束时间
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
        System.out.println(me.getCountTime());

    }

}
