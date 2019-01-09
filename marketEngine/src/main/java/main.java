import com.ats.marketEngine.entity.Order;
import com.ats.marketEngine.service.MarchEngine;

/**
 * Creator:guxt Date 2019/01/08
 */
public class main {

    public static void main(String[] args) {
        MarchEngine me = new MarchEngine();
        Order order = new Order();
        order.setMarketID(1);
        order.setPrice(1);
        order.setQty(1000);
        order.setSide(1);
        order.setTraderID(10);
        int orderid = me.placeAOrder(order);

        Order order1 = new Order();
        order1.setMarketID(1);
        order1.setPrice(1);
        order1.setQty(10000);
        order1.setSide(0);
        order1.setTraderID(11);
        int orderid1 = me.placeAOrder(order1);

        Order order2 = new Order();
        order2.setMarketID(1);
        order2.setPrice(1);
        order2.setQty(1000);
        order2.setSide(1);
        order2.setTraderID(12);
        int orderid2 = me.placeAOrder(order2);

        System.out.println("OrderId:" + orderid);
        System.out.println("OrderId:" + orderid1);
        System.out.println("OrderId:" + orderid2);
    }

}
