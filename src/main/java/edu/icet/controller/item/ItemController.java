package edu.icet.controller.item;


import edu.icet.model.Product;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class ItemController {

    public Label lblProductId;
    public ImageView imgProduct;
    public Label lblProductPrice;

    public void setData(Product product) {

        try {
            Image img1 = new Image(product.getImagePath());
            lblProductPrice.setText(String.valueOf("Rs."+ product.getPrice()));
            imgProduct.setImage(img1);
            lblProductId.setText(product.getProductId());


        } catch (Exception e) {
            System.out.println("Error");
        }
    }


}
