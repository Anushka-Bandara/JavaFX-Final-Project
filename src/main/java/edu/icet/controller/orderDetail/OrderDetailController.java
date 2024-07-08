package edu.icet.controller.orderDetail;

import edu.icet.bo.BoFactory;
import edu.icet.bo.custom.OrderDetailBo;
import edu.icet.model.orderDetail.OrderDetail;
import edu.icet.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailController {
    private static OrderDetailController instance;

    private OrderDetailBo orderDetailBo = BoFactory.getInstance().getBo(BoType.ORDERDETAIL);

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

        return orderDetailBo.save(orderDetail);
//        try {
//            Object isAdd = CrudUtil.execute(
//                    "INSERT INTO orderdetails VALUES(?, ?, ?, ?,?,?)",
//                    orderDetail.getOrderId(),
//                    orderDetail.getProductId(),
//                    orderDetail.getSize(),
//                    orderDetail.getCategory(),
//                    orderDetail.getPrice(),
//                    orderDetail.getQty()
//            );
//            return (Boolean) isAdd;
//        } catch (SQLException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
    }

    public ObservableList<OrderDetail> getAllOrderDetails(){
        return orderDetailBo.getAll();
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
