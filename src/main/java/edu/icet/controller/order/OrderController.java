package edu.icet.controller.order;

import edu.icet.bo.BoFactory;
import edu.icet.bo.custom.OrderBo;
import edu.icet.model.order.Order;
import edu.icet.util.BoType;
import edu.icet.util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderController {

    private static OrderController instance;


    private OrderController(){}

    public static OrderController getInstance(){
        if(instance==null){
            return instance=new OrderController();
        }
        return instance;
    }

    public ObservableList<Order> getAllOredres(){
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM orders");
            ObservableList<Order> listTable = FXCollections.observableArrayList();
            while (resultSet.next()) {
                listTable.add(
                        new Order(
                                resultSet.getString("userId"),
                                resultSet.getString("orderID"),
                                resultSet.getDate("orderDate"),
                                resultSet.getString("paymentType"),
                                resultSet.getDouble("total")
                        )
                );
            }
            return listTable;

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean placeOrder(Order order)  {

        return false;
//        try {
//            return CrudUtil.execute(
//                    "INSERT INTO Orders (userId,orderId, orderDate, paymentType, total) VALUES (?, ?, ?, ?, ?)",
//                    order.getUserId(),order.getOrderId(),order.getOrderDate(),order.getPaymentType(),order.getTotal());
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//            return false;
//        }
    }

}
