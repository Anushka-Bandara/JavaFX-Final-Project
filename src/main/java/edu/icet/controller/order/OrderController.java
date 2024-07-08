package edu.icet.controller.order;

import edu.icet.bo.BoFactory;
import edu.icet.bo.custom.OrderBo;
import edu.icet.model.order.Order;
import edu.icet.util.BoType;
import javafx.collections.ObservableList;

import java.util.List;

public class OrderController {

    private static OrderController instance;

    private OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);

    private OrderController(){}

    public static OrderController getInstance(){
        if(instance==null){
            return instance=new OrderController();
        }
        return instance;
    }

    public ObservableList<Order> getAllOredres(){
        return orderBo.getOrders();
    }

    public boolean placeOrder(Order order)  {
        return orderBo.saveOrder(order);
    }

    public String genarateId() {

        String newId = "O001"; // Default ID
        List<String> list =  orderBo.id();

        if (!list.isEmpty()) {
            String lastId = list.get(0);
            int idNumber = Integer.parseInt(lastId.substring(1)) + 1;
            newId = String.format("O%03d", idNumber);
        }
        return newId;
    }
}
