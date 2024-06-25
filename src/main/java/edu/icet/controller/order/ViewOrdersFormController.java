package edu.icet.controller.order;

import com.jfoenix.controls.JFXButton;
import edu.icet.model.order.Order;
import edu.icet.model.order.OrderTbl;
import edu.icet.model.orderDetail.OrderDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewOrdersFormController implements Initializable {
    public JFXButton backToHomebtn;
    public TableView tblOrders;
    public TableColumn colOrdreId;
    public TableColumn colPaymenttype;
    public TableColumn colorderDate;
    public TableColumn colTotal;
    public TableView tblOrderDetails;
    public TableColumn colProductId;
    public TableColumn colSize;
    public TableColumn colCategory;
    public TableColumn colPrice;
    public TableColumn colQty;
    public TableColumn colUserId;

    public void backToHomebtnOnAction(ActionEvent actionEvent) {
        try {
            URL resource = getClass().getResource("/view/managment-forms/order-managment.fxml");

            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            Parent root = fxmlLoader.load();

            // Assuming you want to replace the current scene
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colOrdreId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colorderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colPaymenttype.setCellValueFactory(new PropertyValueFactory<>("PaymentType"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("Size"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("Category"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));

        loadOrderTbl();

        tblOrders.setOnMouseClicked(this::onclick);
    }

    private void loadOrderTbl() {
        ObservableList<Order> tblData = OrderController.getInstance().getAllOredres();
        ObservableList<OrderTbl> tbl = FXCollections.observableArrayList();

        tblData.forEach(order -> {
            tbl.add(
                    new OrderTbl(
                            order.getOrderId(),
                            order.getOrderDate(),
                            order.getPaymentType(),
                            order.getTotal()
                    )
            );
            tblOrders.setItems(tbl);
        });
    }

    public void onclick(MouseEvent mouseEvent) {
        if (tblOrders.getSelectionModel().getSelectedItem() != null) {
            OrderTbl selsectedOrder = (OrderTbl) tblOrders.getSelectionModel().getSelectedItem();
            loadOrderDetail(selsectedOrder.getOrderId());
        }
    }

    private void loadOrderDetail(String orderId) {
        ObservableList<OrderDetail> list=OrderDetailController.getInstance().getSelectedId(orderId);
        ObservableList<OrderDetail> tbl = FXCollections.observableArrayList();

        list.forEach(orderDetail -> {
            tbl.add(
                    new OrderDetail(
                            orderDetail.getOrderId(),
                            orderDetail.getProductId(),
                            orderDetail.getSize(),
                            orderDetail.getCategory(),
                            orderDetail.getPrice(),
                            orderDetail.getQty()
                    )
            );
            tblOrderDetails.setItems(tbl);
        });
    }
}
