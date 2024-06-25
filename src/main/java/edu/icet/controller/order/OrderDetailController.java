package edu.icet.controller.order;

import edu.icet.model.order.Order;
import edu.icet.model.orderDetail.OrderDetail;
import edu.icet.util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailController {
    private static OrderDetailController instance;

    private OrderDetailController(){}

    public static OrderDetailController getInstance(){
        if(instance==null){
            return instance=new OrderDetailController();
        }
        return instance;
    }

    public boolean addOrderDetail(List<OrderDetail> orderDetailList){
        boolean isAdd=false;
        for (OrderDetail orderDetail:orderDetailList){
            isAdd = addOrderDetail(orderDetail);
        }
        if (isAdd){
            return true;
        }
        return false;
    }
    private boolean addOrderDetail(OrderDetail orderDetail){

        try {
            Object isAdd = CrudUtil.execute(
                    "INSERT INTO orderdetails VALUES(?, ?, ?, ?,?,?)",
                    orderDetail.getOrderId(),
                    orderDetail.getProductId(),
                    orderDetail.getSize(),
                    orderDetail.getCategory(),
                    orderDetail.getPrice(),
                    orderDetail.getQty()
            );
            return (Boolean) isAdd;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<OrderDetail> getAllOrderDetails(){
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM orderDetails");
            ObservableList<OrderDetail> listTable = FXCollections.observableArrayList();
            while (resultSet.next()) {
                listTable.add(
                        new OrderDetail(
                                resultSet.getString("orderId"),
                                resultSet.getString("productId"),
                                resultSet.getString("size"),
                                resultSet.getString("category"),
                                resultSet.getDouble("price"),
                                resultSet.getInt("qty")
                        )
                );
            }
            return listTable;

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<OrderDetail> getSelectedId(String orderId){
        ObservableList<OrderDetail> list = getAllOrderDetails();
        ObservableList<OrderDetail> SelectedList = FXCollections.observableArrayList();
        list.forEach(orderDetail -> {
            if(orderId.equals(orderDetail.getOrderId())){
                SelectedList.add(orderDetail);
            }
        });
        return  SelectedList;
    }
}
