package edu.icet.controller.employee;

import com.jfoenix.controls.JFXButton;
import edu.icet.controller.item.ItemController;
import edu.icet.controller.product.ProductController;
import edu.icet.model.Product;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class
EmployeeDashboardFormController implements Initializable {

    private static EmployeeDashboardFormController instance;
    public JFXButton Menubtn;
    public AnchorPane AnchorPaneslider;
    public JFXButton MenuExitbtn;
    public JFXButton ProductListbtn;
    public JFXButton TaskSummarybtn;
    public JFXButton OrderstoProcessbtn;
    public JFXButton ProductManagmentbtn;
    public JFXButton InventoryManagmentbtn;
    public JFXButton OrderManagmentbtn;
    public JFXButton SupplierManagmentbtn;
    public JFXButton Reportingbtn;
    public JFXButton LogOutbtn;
    public JFXButton Mensbtn;
    public JFXButton Womensbtn;
    public JFXButton Kidsbtn;
    public VBox MensPannel;


    public VBox WomensPannel;
    public VBox KidsPannel;
    public Pane TaskSummarryPanel;
    public Pane OrderstoProcessPanel;
    public JFXButton MShortsbtn;
    public JFXButton MTshirtsbtn;
    public JFXButton MShirtsbtn;
    public JFXButton MTrousersbtn;
    public HBox ProductListPanel;

    public ScrollPane MensTshirtScrollPane;
    public GridPane MensItemListGridTShirt;
    public ScrollPane MensShirtsScrollPane;
    public GridPane MensItemListGridShirts;
    public ScrollPane MensTrousersScrollPane;
    public GridPane MensItemListGridTrousers;
    public ScrollPane MensShortScrollPane;
    public GridPane MensItemListGridShorts;

    public ScrollPane WomensTopsScrollPane;
    public GridPane WomensItemListGridTops;
    public ScrollPane WomensBottomScrollPane;
    public GridPane WomensItemListGridBottom;
    public ScrollPane WomensShirtScrollPane;
    public GridPane WomensItemListGridShirt;
    public ScrollPane WomensShortScrollPane;
    public GridPane WomensItemListGridShort;

    public ScrollPane KidsTshirtScrollPane;
    public GridPane KidsItemListGridTshirt;
    public ScrollPane KidsShirtScrollPane;
    public GridPane KidsItemListGridShirt;
    public ScrollPane KidsTrouserScrollPane;
    public GridPane KidsItemListGridTrouser;
    public ScrollPane KidsShortScrollPane;
    public GridPane KidsItemListGridShort;

    public JFXButton WTopsbtn;
    public JFXButton WBottomsbtn;
    public JFXButton WShirtsbtn;
    public JFXButton WShortsbtn;
    public JFXButton KTishirtbtn;
    public JFXButton KShirtbtn;
    public JFXButton KTrousersbtn;
    public JFXButton KShortbtn;

    public static Integer count=0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        AnchorPaneslider.setTranslateX(-260);
        Menubtn.setOnMouseClicked(event -> {
            AnchorPaneslider.setVisible(true);
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(AnchorPaneslider);

            slide.setToX(0);
            slide.play();

            AnchorPaneslider.setTranslateX(-260);

            slide.setOnFinished((ActionEvent e) -> {
                Menubtn.setVisible(false);
                MenuExitbtn.setVisible(true);
            });
        });

        MenuExitbtn.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(AnchorPaneslider);

            slide.setToX(-260);
            slide.play();

            AnchorPaneslider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e) -> {
                Menubtn.setVisible(true);
                MenuExitbtn.setVisible(false);
            });
        });

        load();
    }

    public static EmployeeDashboardFormController getInstance(){
        if(instance==null){
            return instance = new EmployeeDashboardFormController();
        }
        return instance;
    }

     public void load() {
        ObservableList<Product> productlist = ProductController.getInstance().getAllProducts();

        // Initialize row and column for each category grid
        int mensTShirtColumn = 0, mensTShirtRow = 1;
        int mensShirtColumn = 0, mensShirtRow = 1;
        int mensTrouserColumn = 0, mensTrouserRow = 1;
        int mensShortColumn = 0, mensShortRow = 1;

        int womensTopsColumn = 0, womensTopsRow = 1;
        int womensBottomColumn = 0, womensBottomRow = 1;
        int womensShirtColumn = 0, womensShirtRow = 1;
        int womensShortColumn = 0, womensShortRow = 1;

        int kidsTShirtColumn = 0, kidsTShirtRow = 1;
        int kidsTrouserColumn = 0, kidsTrouserRow = 1;
        int kidsShirtColumn = 0, kidsShirtRow = 1;
        int kidsShortColumn = 0, kidsShortRow = 1;

        for (Product product : productlist) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/employee-forms/item.fxml"));
            AnchorPane anchorPane = null;
            try {
                anchorPane = fxmlLoader.load();
            } catch (IOException e) {
                //throw new RuntimeException(e);
                System.out.println("Error in  here");
            }
            ItemController itemController = fxmlLoader.getController();
            itemController.setData(product);

            String productId = product.getProductId().replaceAll("[^A-Za-z]", "");
            switch (product.getCategory()) {
                case "Mens":
                    if (productId.equals("T")) {
                        if (mensTShirtColumn == 3) {
                            mensTShirtColumn = 0;
                            mensTShirtRow++;
                        }
                        MensItemListGridTShirt.add(anchorPane, mensTShirtColumn++, mensTShirtRow);
                        GridPane.setMargin(anchorPane, new Insets(15));
                    } else if (productId.equals("S")) {
                        if (mensShirtColumn == 3) {
                            mensShirtColumn = 0;
                            mensShirtRow++;
                        }
                        MensItemListGridShirts.add(anchorPane, mensShirtColumn++, mensShirtRow);//line190
                        GridPane.setMargin(anchorPane, new Insets(15));
                    } else if (productId.equals("R")) {
                        if (mensTrouserColumn == 3) {
                            mensTrouserColumn = 0;
                            mensTrouserRow++;
                        }
                        MensItemListGridTrousers.add(anchorPane, mensTrouserColumn++, mensTrouserRow);
                        GridPane.setMargin(anchorPane, new Insets(15));
                    } else if (productId.equals("H")) {
                        if (mensShortColumn == 3) {
                            mensShortColumn = 0;
                            mensShortRow++;
                        }
                        MensItemListGridShorts.add(anchorPane, mensShortColumn++, mensShortRow);
                        GridPane.setMargin(anchorPane, new Insets(15));
                    } else {
                        System.out.println("Add New Letter");
                    }
                    break;

                case "Womens":
                    if (productId.equals("T")) {
                        if (womensTopsColumn == 3) {
                            womensTopsColumn = 0;
                            womensTopsRow++;
                        }
                        WomensItemListGridTops.add(anchorPane, womensTopsColumn++, womensTopsRow);
                        GridPane.setMargin(anchorPane, new Insets(15));
                    } else if (productId.equals("B")) {
                        if (womensBottomColumn == 3) {
                            womensBottomColumn = 0;
                            womensBottomRow++;
                        }
                        WomensItemListGridBottom.add(anchorPane, womensBottomColumn++, womensBottomRow);
                        GridPane.setMargin(anchorPane, new Insets(15));
                    } else if (productId.equals("S")) {
                        if (womensShirtColumn == 3) {
                            womensShirtColumn = 0;
                            womensShirtRow++;
                        }
                        WomensItemListGridShirt.add(anchorPane, womensShirtColumn++, womensShirtRow);
                        GridPane.setMargin(anchorPane, new Insets(15));
                    } else if (productId.equals("H")) {
                        if (womensShortColumn == 3) {
                            womensShortColumn = 0;
                            womensShortRow++;
                        }
                        WomensItemListGridShort.add(anchorPane, womensShortColumn++, womensShortRow);
                        GridPane.setMargin(anchorPane, new Insets(15));
                    } else {
                        System.out.println("Add New Letter");
                    }
                    break;

                case "Kids":
                    if (productId.equals("T")) {
                        if (kidsTShirtColumn == 3) {
                            kidsTShirtColumn = 0;
                            kidsTShirtRow++;
                        }
                        KidsItemListGridTshirt.add(anchorPane, kidsTShirtColumn++, kidsTShirtRow);
                        GridPane.setMargin(anchorPane, new Insets(15));
                    } else if (productId.equals("R")) {
                        if (kidsTrouserColumn == 3) {
                            kidsTrouserColumn = 0;
                            kidsTrouserRow++;
                        }
                        KidsItemListGridTrouser.add(anchorPane, kidsTrouserColumn++, kidsTrouserRow);
                        GridPane.setMargin(anchorPane, new Insets(15));
                    } else if (productId.equals("S")) {
                        if (kidsShirtColumn == 3) {
                            kidsShirtColumn = 0;
                            kidsShirtRow++;
                        }
                        KidsItemListGridShirt.add(anchorPane, kidsShirtColumn++, kidsShirtRow);
                        GridPane.setMargin(anchorPane, new Insets(15));
                    } else if (productId.equals("H")) {
                        if (kidsShortColumn == 3) {
                            kidsShortColumn = 0;
                            kidsShortRow++;
                        }
                        System.out.println(product);
                        KidsItemListGridShort.add(anchorPane, kidsShortColumn++, kidsShortRow);
                        GridPane.setMargin(anchorPane, new Insets(15));
                    } else {
                        System.out.println("Add New Letter");
                    }
                    break;

                default:
                    System.out.println("Unknown category: " + product.getCategory());
                    break;
            }
        }
    }


    public void ProductListbtnOnAction(ActionEvent actionEvent) {
        ProductListPanel.setVisible(true);
        TaskSummarryPanel.setVisible(false);
        OrderstoProcessPanel.setVisible(false);

    }

    public void TaskSummarybtnOnAction(ActionEvent actionEvent) {
        ProductListPanel.setVisible(false);
        TaskSummarryPanel.setVisible(true);
        OrderstoProcessPanel.setVisible(false);
    }

    public void OrderstoProcessbtnOnAction(ActionEvent actionEvent) {
        ProductListPanel.setVisible(false);
        TaskSummarryPanel.setVisible(false);
        OrderstoProcessPanel.setVisible(true);
    }



//Anchor Panel  Managment btn Actions
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
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("First Close the Opened Window");
            alert.show();
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
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("First Close the Opened Window");
            alert.show();
        }

    }

    public void OrderManagmentbtnOnAction(ActionEvent actionEvent) {

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
       }else{
           Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.setContentText("First Close the Opened Window");
           alert.show();
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
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("First Close the Opened Window");
            alert.show();
        }
    }

    public void ReportingbtnOnAction(ActionEvent actionEvent) {
        try {
            URL resource = getClass().getResource("/view/");

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



    public void LogOutbtnOnAction(ActionEvent actionEvent) {
        try {
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




    //MensbtnActions


    public void MensbtnOnAction(ActionEvent actionEvent) {
        MensPannel.setVisible(true);
        WomensPannel.setVisible(false);
        KidsPannel.setVisible(false);
    }

    public void MShortsbtnOnAction(ActionEvent actionEvent) {
        MensTshirtScrollPane.setVisible(false);
        MensShirtsScrollPane.setVisible(false);
        MensTrousersScrollPane.setVisible(false);
        MensShortScrollPane.setVisible(true);
    }

    public void MTshirtsbtnOnAction(ActionEvent actionEvent) {
        MensTshirtScrollPane.setVisible(true);
        MensShirtsScrollPane.setVisible(false);
        MensTrousersScrollPane.setVisible(false);
        MensShortScrollPane.setVisible(false);
    }

    public void MShirtsbtnOnAction(ActionEvent actionEvent) {
        MensTshirtScrollPane.setVisible(false);
        MensShirtsScrollPane.setVisible(true);
        MensTrousersScrollPane.setVisible(false);
        MensShortScrollPane.setVisible(false);
    }

    public void MTrousersbtnOnAction(ActionEvent actionEvent) {
        MensTshirtScrollPane.setVisible(false);
        MensShirtsScrollPane.setVisible(false);
        MensTrousersScrollPane.setVisible(true);
        MensShortScrollPane.setVisible(false);
    }

    //WomensBtnActions


    public void WomensbtnOnAction(ActionEvent actionEvent) {
        MensPannel.setVisible(false);
        WomensPannel.setVisible(true);
        KidsPannel.setVisible(false);
    }

    public void WTopsbtnOnAction(ActionEvent actionEvent) {
        WomensTopsScrollPane.setVisible(true);
        WomensShirtScrollPane.setVisible(false);
        WomensShortScrollPane.setVisible(false);
        WomensBottomScrollPane.setVisible(false);
    }

    public void WBottomsbtnOnAction(ActionEvent actionEvent) {
        WomensTopsScrollPane.setVisible(false);
        WomensShirtScrollPane.setVisible(false);
        WomensShortScrollPane.setVisible(false);
        WomensBottomScrollPane.setVisible(true);
    }

    public void WShirtsbtnOnAction(ActionEvent actionEvent) {
        WomensTopsScrollPane.setVisible(false);
        WomensShirtScrollPane.setVisible(true);
        WomensShortScrollPane.setVisible(false);
        WomensBottomScrollPane.setVisible(false);
    }

    public void WShortsbtnOnAction(ActionEvent actionEvent) {
        WomensTopsScrollPane.setVisible(false);
        WomensShirtScrollPane.setVisible(false);
        WomensShortScrollPane.setVisible(true);
        WomensBottomScrollPane.setVisible(false);
    }



    //KidsBtnOnAction
    public void KidsbtnOnAction(ActionEvent actionEvent) {
        MensPannel.setVisible(false);
        WomensPannel.setVisible(false);
        KidsPannel.setVisible(true);
    }

    public void KTishirtbtnOnAction(ActionEvent actionEvent) {
        KidsTshirtScrollPane.setVisible(true);
        KidsShirtScrollPane.setVisible(false);
        KidsTrouserScrollPane.setVisible(false);
        KidsShortScrollPane.setVisible(false);
    }

    public void KShirtbtnOnAction(ActionEvent actionEvent) {
        KidsTshirtScrollPane.setVisible(false);
        KidsShirtScrollPane.setVisible(false);
        KidsTrouserScrollPane.setVisible(true);
        KidsShortScrollPane.setVisible(false);
    }

    public void KTrousersbtnOnAction(ActionEvent actionEvent) {
        KidsTshirtScrollPane.setVisible(false);
        KidsShirtScrollPane.setVisible(false);
        KidsTrouserScrollPane.setVisible(true);
        KidsShortScrollPane.setVisible(false);
    }

    public void KShortbtnOnAction(ActionEvent actionEvent) {
        KidsTshirtScrollPane.setVisible(false);
        KidsShirtScrollPane.setVisible(false);
        KidsTrouserScrollPane.setVisible(false);
        KidsShortScrollPane.setVisible(true);
    }
}
