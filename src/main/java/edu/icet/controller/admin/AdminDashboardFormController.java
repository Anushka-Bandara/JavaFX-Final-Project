package edu.icet.controller.admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.icet.controller.order.OrderController;
import edu.icet.controller.order.OrderDetailController;
import edu.icet.controller.product.ProductController;
import edu.icet.controller.user.UserController;
import edu.icet.model.user.User;
import edu.icet.model.inventory.InventoryTable;
import edu.icet.model.Product;
import edu.icet.model.order.Order;
import edu.icet.model.orderDetail.OrderDetail;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class AdminDashboardFormController implements Initializable {
    public JFXButton Menubtn;
    public JFXButton MenuExitbtn;
    public AnchorPane AnchorPaneslider;
    public StackPane SalesSummaryPane;
    public StackPane UserRegistrationPane;
    public StackPane InventoryStatusPane;
    public StackPane RecentOrdersPane;
    public HBox AnnuallySalesChartBox;
    public HBox MonthlySalesChartBox;
    public HBox DailySalesChartBox;
    public JFXButton dailybtn;
    public JFXButton monthlybtn;
    public JFXButton annuallybtn;

    public JFXButton ProductManagmentbtn;
    public JFXButton InventoryManagmentbtn;
    public JFXButton OrderManagmentbtn;
    public JFXButton SupplierManagmentbtn;
    public JFXButton Reportingbtn;
    public JFXButton SalesSummarybtn;
    public JFXButton UserRegisterbtn;
    public JFXButton InventoryStatusbtn;
    public JFXButton RecentOredresbtn;

    public static Integer count=0;
    public JFXButton ViewUsersbtn;
    public TableView tblInventoryStates;
    public TableColumn colProductID;
    public TableColumn colProductName;
    public TableColumn colCategory;
    public TableColumn colSize;
    public TableColumn colQty;
    public TableColumn colPrice;
    public JFXButton inventorybtnSearch;
    public JFXTextField txtinventoryfield;
    public JFXButton reloadInventorybtn;
    public PieChart pieChartA;
    public BarChart barChartA;
    public PieChart pieChartM;
    public BarChart barChartM;
    public BarChart barChartD;
    public PieChart pieChartD;
    public JFXButton btnLogOut;
    public JFXTextField txtUserId;
    public JFXTextField txtuserEmail;
    public JFXTextField txtUserName;
    public JFXTextField txtUserPassword;
    public JFXButton btnAddUser;
    public JFXButton btnUpdateUser;
    public JFXTextField txtUserRole;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        AnchorPaneslider.setTranslateX(-259);
        Menubtn.setOnMouseClicked(event -> {
            AnchorPaneslider.setVisible(true);
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(AnchorPaneslider);

            slide.setToX(0);
            slide.play();

            AnchorPaneslider.setTranslateX(-259);

            slide.setOnFinished((ActionEvent e) -> {
                Menubtn.setVisible(false);
                MenuExitbtn.setVisible(true);
            });
        });
        MenuExitbtn.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(AnchorPaneslider);

            slide.setToX(-259);
            slide.play();

            AnchorPaneslider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e) -> {
                Menubtn.setVisible(true);
                MenuExitbtn.setVisible(false);
            });
        });

        colProductID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("Category"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("Size"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));


        if(txtinventoryfield.getText().equals("")){
            loadtableInventory();
        }

        loadDailySalesChart();
        loadDailySalesPieChart();
    }


    public void loadDailySalesChart() {
        ObservableList<Order> orderList = OrderController.getInstance().getAllOredres();

        XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName("Daily Sales");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        orderList.forEach(order -> {
            String dateString = dateFormat.format(order.getOrderDate());

            XYChart.Data<String, Double> data = new XYChart.Data<>(dateString, order.getTotal());

            // Set color for the bar
            data.nodeProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    newValue.setStyle("-fx-bar-fill: " + getColorForOrder(order));
                }
            });
            series.getData().add(data);
        });

        barChartD.getData().clear();
        barChartD.getData().add(series);

        CategoryAxis xAxis = (CategoryAxis) barChartD.getXAxis();
        NumberAxis yAxis = (NumberAxis) barChartD.getYAxis();

        // Customize axes if needed
        xAxis.setLabel("Date");
        yAxis.setLabel("Total Sales");

    }
    private void loadMonthlySalesChart() {
        ObservableList<Order> orderList = OrderController.getInstance().getAllOredres();

        XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName("Monthly Sales");

        SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");

        Map<String, Double> monthlySales = new HashMap<>();
        orderList.forEach(order -> {
            String monthString = monthFormat.format(order.getOrderDate());
            monthlySales.put(monthString, monthlySales.getOrDefault(monthString, 0.0) + order.getTotal());
        });

        monthlySales.forEach((month, total) -> {
            XYChart.Data<String, Double> data = new XYChart.Data<>(month, total);
            data.nodeProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    newValue.setStyle("-fx-bar-fill: blue");
                }
            });
            series.getData().add(data);
        });

        barChartM.getData().clear();
        barChartM.getData().add(series);

        CategoryAxis xAxis = (CategoryAxis) barChartM.getXAxis();
        NumberAxis yAxis = (NumberAxis) barChartM.getYAxis();

        xAxis.setLabel("Month");
        yAxis.setLabel("Total Sales");
    }
    private void loadAnnuallySalesChart() {
        ObservableList<Order> orderList = OrderController.getInstance().getAllOredres();

        XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName("Annual Sales");

        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

        Map<String, Double> annualSales = new HashMap<>();
        orderList.forEach(order -> {
            String yearString = yearFormat.format(order.getOrderDate());
            annualSales.put(yearString, annualSales.getOrDefault(yearString, 0.0) + order.getTotal());
        });

        annualSales.forEach((year, total) -> {
            XYChart.Data<String, Double> data = new XYChart.Data<>(year, total);
            data.nodeProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    newValue.setStyle("-fx-bar-fill: red");
                }
            });
            series.getData().add(data);
        });

        barChartA.getData().clear();
        barChartA.getData().add(series);

        CategoryAxis xAxis = (CategoryAxis) barChartA.getXAxis();
        NumberAxis yAxis = (NumberAxis) barChartA.getYAxis();

        xAxis.setLabel("Year");
        yAxis.setLabel("Total Sales");
    }

    // Method to load daily sales pie chart
    private void loadDailySalesPieChart() {
        ObservableList<Order> orderList = OrderController.getInstance().getAllOredres();
        ObservableList<OrderDetail> orderDetailList = OrderDetailController.getInstance().getAllOrderDetails();


        Map<String, Double> dailySales = new HashMap<>();
        dailySales.put("Kids", 10.0);
        dailySales.put("Mens", 20.0);
        dailySales.put("Womens", 60.0);

        orderList.forEach(order -> {

            orderDetailList.forEach(orderDetail -> {

                if (order.getOrderId().equals(orderDetail.getOrderId())) {
                    String category = orderDetail.getCategory();
                   // dailySales.put(category, dailySales.get(category) + orderDetail.getPrice() * orderDetail.getQty());

                }

            });

        });

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Kids", dailySales.get("Kids")),
                new PieChart.Data("Mens", dailySales.get("Mens")),
                new PieChart.Data("Womens", dailySales.get("Womens"))
        );

        pieChartD.setData(pieChartData);
    }


    // Method to load monthly sales pie chart
    private void loadMonthlySalesPieChart() {
        ObservableList<Order> orderList = OrderController.getInstance().getAllOredres();
        ObservableList<OrderDetail> orderDetailList = OrderDetailController.getInstance().getAllOrderDetails();

        Map<String, Double> monthlySales = new HashMap<>();
        monthlySales.put("Kids", 20.0);
        monthlySales.put("Mens", 15.0);
        monthlySales.put("Womens", 40.0);

//        orderList.forEach(order -> {
//            orderDetailList.forEach(orderDetail -> {
//                if (order.getOrderId().equals(orderDetail.getId())) {
//                    String category = orderDetail.getCategory();
//                    monthlySales.put(category, monthlySales.get(category) + orderDetail.getPrice() * orderDetail.getQty());
//                }
//            });
//        });

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Kids", monthlySales.get("Kids")),
                new PieChart.Data("Mens", monthlySales.get("Mens")),
                new PieChart.Data("Womens", monthlySales.get("Womens"))
        );

        pieChartM.setData(pieChartData);
    }

    // Method to load annual sales pie chart
    private void loadAnnuallySalesPieChart() {
        ObservableList<Order> orderList = OrderController.getInstance().getAllOredres();
        ObservableList<OrderDetail> orderDetailList = OrderDetailController.getInstance().getAllOrderDetails();

        Map<String, Double> annualSales = new HashMap<>();
        annualSales.put("Kids", 20.0);
        annualSales.put("Mens", 10.0);
        annualSales.put("Womens", 50.0);

//        orderList.forEach(order -> {
//            orderDetailList.forEach(orderDetail -> {
//                if (order.getOrderId().equals(orderDetail.getId())) {
//                    String category = orderDetail.getCategory();
//                    annualSales.put(category, annualSales.get(category) + orderDetail.getPrice() * orderDetail.getQty());
//                }
//            });
//        });

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Kids", annualSales.get("Kids")),
                new PieChart.Data("Mens", annualSales.get("Mens")),
                new PieChart.Data("Womens", annualSales.get("Womens"))
        );

        pieChartA.setData(pieChartData);
    }

    private String getColorForOrder(Order order) {
        // Logic to determine color based on order properties, e.g., order total
        double total = order.getTotal();
        if (total < 100) {
            return "green";
        } else if (total < 500) {
            return "yellow";
        } else {
            return "black";
        }
    }


    private void loadtableInventory() {
        ObservableList<Product> tblData = ProductController.getInstance().getAllProducts();
        ObservableList<InventoryTable> tbl = FXCollections.observableArrayList();

        tblData.forEach(product -> {
            tbl.add(
                    new InventoryTable(
                            product.getProductId(),
                            product.getName(),
                            product.getCategory(),
                            product.getSize(),
                            product.getQty(),
                            product.getPrice()
                    )
            );
            tblInventoryStates.setItems(tbl);
        });
    }

    public void MenuTriggerbtnOnAction(ActionEvent actionEvent) {
    }

    public void SalesSummaryOnAction(ActionEvent actionEvent) {
        MenuClose();
        SalesSummaryPane.setVisible(true);
        UserRegistrationPane.setVisible(false);
        InventoryStatusPane.setVisible(false);
        RecentOrdersPane.setVisible(false);

    }
    public void InventoryStatusOnAction(ActionEvent actionEvent) {
        MenuClose();
        SalesSummaryPane.setVisible(false);
        UserRegistrationPane.setVisible(false);
        InventoryStatusPane.setVisible(true);
        RecentOrdersPane.setVisible(false);
    }
    public void UserRegistrationOnAction(ActionEvent actionEvent) {
        MenuClose();
        SalesSummaryPane.setVisible(false);
        UserRegistrationPane.setVisible(true);
        InventoryStatusPane.setVisible(false);
        RecentOrdersPane.setVisible(false);
        txtUserId.setText(UserController.getInstance().genarateUserId());
    }
    public void RecentOrdersOnAction(ActionEvent actionEvent) {
        MenuClose();
        SalesSummaryPane.setVisible(false);
        UserRegistrationPane.setVisible(false);
        InventoryStatusPane.setVisible(false);
        RecentOrdersPane.setVisible(true);
    }

    public void MenuClose() {

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(AnchorPaneslider);

        slide.setToX(-259);
        slide.play();

        AnchorPaneslider.setTranslateX(0);

        slide.setOnFinished((ActionEvent e) -> {
            Menubtn.setVisible(true);
            MenuExitbtn.setVisible(false);
        });

    }

    public void DailybtnOnAction(ActionEvent actionEvent) {
        AnnuallySalesChartBox.setVisible(false);
        MonthlySalesChartBox.setVisible(false);
        DailySalesChartBox.setVisible(true);

        loadDailySalesChart();
        loadDailySalesPieChart();
    }
    public void MonthlybtnOnAction(ActionEvent actionEvent) {
        AnnuallySalesChartBox.setVisible(false);
        MonthlySalesChartBox.setVisible(true);
        DailySalesChartBox.setVisible(false);

        loadMonthlySalesChart();
        loadMonthlySalesPieChart();
    }
    public void AnnuallybtnOnAction(ActionEvent actionEvent) {
        AnnuallySalesChartBox.setVisible(true);
        MonthlySalesChartBox.setVisible(false);
        DailySalesChartBox.setVisible(false);

        loadAnnuallySalesChart();
        loadAnnuallySalesPieChart();
    }



    //Anchor Pane  Managmentbtn's
    public void ProductManagmentbtnOnAction(ActionEvent actionEvent) {
        if (count==0){
            try {
                URL resource = getClass().getResource("/view/managment-forms/product-managment.fxml");
                //how to set the resourses url to last opened fxml file path

                FXMLLoader fxmlLoader = new FXMLLoader(resource);
                Parent root = fxmlLoader.load();

                // Assuming you want to replace the current scene
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                count++;

                stage.setOnHidden((WindowEvent event) -> count = 0);
                stage.setOnCloseRequest((WindowEvent event) -> count = 0);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void InventoryManagmentbtnOnAction(ActionEvent actionEvent) {

        if(count==0){
            try {
                URL resource = getClass().getResource("/view/managment-forms/inventory-managment.fxml");
                //how to set the resourses url to last opened fxml file path

                FXMLLoader fxmlLoader = new FXMLLoader(resource);
                Parent root = fxmlLoader.load();

                // Assuming you want to replace the current scene
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                count++;

                stage.setOnHidden((WindowEvent event) -> count = 0);
                stage.setOnCloseRequest((WindowEvent event) -> count = 0);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void OrderManagmentbtnOnAction(ActionEvent actionEvent) {
//        try {
//            URL resource = getClass().getResource("/view/order-managment.fxml");
//
//            FXMLLoader fxmlLoader = new FXMLLoader(resource);
//            Parent root = fxmlLoader.load();
//
//            // Assuming you want to replace the current scene
//            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//            stage.setScene(new Scene(root));
//            stage.show();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        if(count==0){
            try {
                URL resource = getClass().getResource("/view/managment-forms/order-managment.fxml");
                //how to set the resourses url to last opened fxml file path

                FXMLLoader fxmlLoader = new FXMLLoader(resource);
                Parent root = fxmlLoader.load();

                // Assuming you want to replace the current scene
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                count++;

                stage.setOnHidden((WindowEvent event) -> count = 0);
                stage.setOnCloseRequest((WindowEvent event) -> count = 0);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void SupplierManagmentbtnOnAction(ActionEvent actionEvent) {

        if(count==0){
            try {
                URL resource = getClass().getResource("/view/managment-forms/supplier-managment.fxml");
                //how to set the resourses url to last opened fxml file path

                FXMLLoader fxmlLoader = new FXMLLoader(resource);
                Parent root = fxmlLoader.load();

                // Assuming you want to replace the current scene
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                count++;

                stage.setOnHidden((WindowEvent event) -> count = 0);
                stage.setOnCloseRequest((WindowEvent event) -> count = 0);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void ReportingbtnOnAction(ActionEvent actionEvent) {
//        try {
//            URL resource = getClass().getResource("/view/");
//
//            FXMLLoader fxmlLoader = new FXMLLoader(resource);
//            Parent root = fxmlLoader.load();
//
//            // Assuming you want to replace the current scene
//            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//            stage.setScene(new Scene(root));
//            stage.show();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    public void ViewUsersbtnOnAction(ActionEvent actionEvent) {
        try {
            URL resource = getClass().getResource("/view/admin-forms/User-View.fxml");

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
    public void inventorybtnSearchonAction(ActionEvent actionEvent) {

        ObservableList<Product> productIdlist = ProductController.getInstance().getAllProducts();
        ObservableList<InventoryTable> tbl = FXCollections.observableArrayList();
        String productID = txtinventoryfield.getText();

        productIdlist.forEach(product -> {
            if(productID.equals(product.getProductId())){
                tbl.add(
                        new InventoryTable(
                                product.getProductId(),
                                product.getName(),
                                product.getCategory(),
                                product.getSize(),
                                product.getQty(),
                                product.getPrice()
                        )
                );
                tblInventoryStates.setItems(tbl);
            }
        });
    }
    public void reloadInventorybtnOnAction(ActionEvent actionEvent) {
        txtinventoryfield.setText("");
        loadtableInventory();
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) {        try {
            URL resource = getClass().getResource("/view/employee-forms/user-login.fxml");

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

    public void btnAddUserOnAction(ActionEvent actionEvent) {
        String userId = txtUserId.getText();
        String name=txtUserName.getText();
        String email=txtuserEmail.getText();
        String password=txtUserPassword.getText();
        String role=txtUserRole.getText();

        Boolean isAdd = UserController.getInstance().addUser(new User(userId,name,email,password,role));
        if(isAdd){
            showAlert(Alert.AlertType.INFORMATION,"alert","User Added Successfully");
            txtUserId.setText(UserController.getInstance().genarateUserId());
            clearFields();
        }else{
            showAlert(Alert.AlertType.ERROR,"alert","User no Added");
            clearFields();
        }
    }

    private void clearFields() {
        txtUserName.setText("");
        txtuserEmail.setText("");
        txtUserPassword.setText("");
        txtUserRole.setText("");

    }

    public void btnUpdateUserOnAction(ActionEvent actionEvent) {
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
