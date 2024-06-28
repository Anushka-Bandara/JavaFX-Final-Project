package edu.icet.controller.order;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.bo.BoFactory;
import edu.icet.bo.custom.OrderBo;
import edu.icet.controller.orderDetail.OrderDetailController;
import edu.icet.controller.product.ProductController;
import edu.icet.controller.user.UserController;
import edu.icet.db.DBConnection;
import edu.icet.model.Product;
import edu.icet.model.order.CartTable;
import edu.icet.model.order.Order;
import edu.icet.model.orderDetail.OrderDetail;
import edu.icet.model.user.User;
import edu.icet.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class OrderManagmentFormController implements Initializable {

    public Label ViewPastOrdersbtn;
    public JFXComboBox<String> cmbPaymentType;
    public JFXTextField txtQty;
    public JFXTextField txtPrice;
    public JFXComboBox cmbProductID;
    public TableColumn colCategory;
    public JFXButton btnReturnOrder;
    public JFXButton btnPlaceOrder;
    public JFXButton btnAdd;
    public TableColumn colproductId;
    public TableColumn colSize;
    public TableColumn colPrice;
    public TableColumn colQty;
    public TableView tblCart;
    public TableColumn colsubTotal;
    public Label lbltotal;
    public JFXTextField txtSize;
    public JFXTextField txtCategory;
    public Label lblOrderId;

    private OrderBo orderBo= BoFactory.getInstance().getBo(BoType.ORDER);

    ObservableList<CartTable> list = FXCollections.observableArrayList();

    public void ViewPastOrdersbtnOnAction(MouseEvent mouseEvent) {
        try {
            URL resource = getClass().getResource("/view/managment-forms/view-order.fxml");

            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            Parent root = fxmlLoader.load();

            // Assuming you want to replace the current scene
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        colproductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colsubTotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        lblOrderId.setText(generateOrderId());
        loadInitialValues();
        loadProductIds();

    }

    private ObservableList<String> loadProductIds(){
        ObservableList<String> productIdlist = FXCollections.observableArrayList();
        ObservableList<Product> products = ProductController.getInstance().getAllProducts();

        products.forEach(product -> {
            productIdlist.add(product.getProductId());
        });

        return productIdlist;

    }

    private void loadInitialValues() {
        cmbProductID.setItems(loadProductIds());

        ObservableList<String> paymentTypes = FXCollections.observableArrayList("Card", "Cash", "Transaction");
        cmbPaymentType.setItems(paymentTypes);
    }

    public void btnReturnOrderOnAction(ActionEvent actionEvent) {
        User user = UserController.loginuser;
        System.out.println(user);
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {


        Order order = new Order(
                UserController.loginuser.getUserId(),
                lblOrderId.getText(),
                getDate(),
                cmbPaymentType.getValue(),
                getTotal()
        );

        boolean b = orderBo.saveOrder(order);

        List<OrderDetail> orderDetailList = new ArrayList<>();

        for (CartTable cartTbl : list) {
            String oID = lblOrderId.getText();
            String productId = cartTbl.getProductId();
            String size = cartTbl.getSize();
            String category = cartTbl.getCategory();
            Double price = cartTbl.getPrice();
            Integer qty = cartTbl.getQty();
            orderDetailList.add(new OrderDetail(oID,productId,size,category,price,qty));
        }
        if(b){
            Boolean isAdd = OrderDetailController.getInstance().addOrderDetail(orderDetailList);
            if(isAdd){
                showAlert(Alert.AlertType.INFORMATION,"OrderAdded","OrderAdded Succesfully");
                clearFields();
                lblOrderId.setText(generateOrderId());
                tblCart.setItems(null);
            }else {
                showAlert(Alert.AlertType.ERROR,"order","Order not Added");
                clearFields();
            }
        }

//        Boolean isAdded = OrderController.getInstance().placeOrder(
//                new Order(
//                        UserController.loginuser.getUserId(),
//                        lblOrderId.getText(),
//                        getDate(),
//                        cmbPaymentType.getValue(),
//                        getTotal()
//                )
//        );
//        List<OrderDetail> orderDetailList = new ArrayList<>();
//
//        for (CartTable cartTbl : list) {
//            String oID = lblOrderId.getText();
//            String productId = cartTbl.getProductId();
//            String size = cartTbl.getSize();
//            String category = cartTbl.getCategory();
//            Double price = cartTbl.getPrice();
//            Integer qty = cartTbl.getQty();
//            orderDetailList.add(new OrderDetail(oID,productId,size,category,price,qty));
//        }
//        if(isAdded){
//            Boolean isAdd = OrderDetailController.getInstance().addOrderDetail(orderDetailList);
//            if(isAdd){
//                showAlert(Alert.AlertType.INFORMATION,"OrderAdded","OrderAdded Succesfully");
//                clearFields();
//                lblOrderId.setText(generateOrderId());
//                tblCart.setItems(null);
//            }else {
//                showAlert(Alert.AlertType.ERROR,"order","Order not Added");
//                clearFields();
//            }
//        }

    }

    private void clearFields() {
        cmbProductID.setValue(null);
        txtPrice.setText("");
        txtQty.setText("");
        cmbPaymentType.setValue(null);
        lbltotal.setText("0.00/=");
    }


    private Double  getTotal() {
        String lblTotalText = lbltotal.getText();
        String numericValueString = lblTotalText.replace("/=", "").trim();
        return Double.parseDouble(numericValueString);
    }

    private String generateOrderId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1");
            if (resultSet.next()) {
                String lastId = resultSet.getString("orderId");
                int newId = Integer.parseInt(lastId.substring(1)) + 1;
                return String.format("O%03d", newId);
            } else {
                return "O001"; // Default ID if no order exist
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return "O001"; // Fallback ID
        }
    }

    private Date getDate(){
        Date date = new Date();

        return (date);

    }

    public void btnItemAddOnAction(ActionEvent actionEvent) {
        String userId = UserController.loginuser.getUserId();
        String productId = (String) cmbProductID.getValue();
        String size = txtSize.getText();
        String category = txtCategory.getText();
        Double price = Double.parseDouble(txtPrice.getText());
        Integer qty = Integer.parseInt(txtQty.getText());
        Double subtotal = getItemsTotal(qty,price);

        CartTable cartTable = new CartTable(userId,productId,size,category,price,qty,subtotal);
        list.add(cartTable);
        tblCart.setItems(list);

        setTotal(subtotal);
    }

    private void setTotal(Double subtotal) {
        String lblTotalText = lbltotal.getText();
        String numericValueString = lblTotalText.replace("/=", "").trim();
        Double previousValue = Double.parseDouble(numericValueString);
        previousValue+=subtotal;
        lbltotal.setText(previousValue+"/=");
    }

    private Double getItemsTotal(Integer qty, Double price) {
        return qty*price;
    }

    public void cmbproductIdonAction(ActionEvent actionEvent) {
        String pId = (String) cmbProductID.getValue();
        Product product = ProductController.getInstance().getSize(pId);

        txtSize.setText(product.getSize());
        txtCategory.setText(product.getCategory());


    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
