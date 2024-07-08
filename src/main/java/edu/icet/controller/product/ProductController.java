package edu.icet.controller.product;

import edu.icet.bo.BoFactory;
import edu.icet.bo.custom.ProductBo;

import edu.icet.entity.ProductEntity;
import edu.icet.model.Product;
import edu.icet.util.BoType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class ProductController {
    private static ProductController instance;

    private ProductBo productBo = BoFactory.getInstance().getBo(BoType.PRODUCT);

    private ProductController(){}

    public static ProductController getInstance(){
        if(instance==null){
            return instance = new ProductController();
        }
        return instance;
    }

    public ObservableList<Product> getAllProducts() throws RuntimeException {
        return productBo.getAllProduct();
    }

    public ObservableList<String> getProductIds(){
        ObservableList<String> productIdlist = FXCollections.observableArrayList();
        ObservableList<Product> products = getAllProducts();
        products.forEach(product -> {
            productIdlist.add(product.getProductId());
        });
        return productIdlist;

    }

    public Product getSize(String productId){

        ObservableList<Product> products = getAllProducts();
        for (Product selectProduct : products) {
            if (productId.equals(selectProduct.getProductId())) {
                return selectProduct;
            }
        }
        return null;
    }

    public Boolean saveProduct(Product product) {

        if(product.getImagePath()==null && product.getName()==null){
            return false;
        }
        return productBo.saveProduct(product);
    }

    public boolean updateProduct(Product product) {
        return productBo.updateProduct(product);
   }

    public boolean stockManagment(String productId,Integer Qty) {
        return productBo.updateStock(productId,Qty);
    }



    public Product getSelectProduct(String productId){
        ObservableList<Product> list = getAllProducts();
        for(Product product : list){
            if(productId.equals(product.getProductId())){
                return product;
            }
        }
        return null;
    }

    public boolean deleteProduct(String productId) {
        return productBo.deleteProduct(productId);
    }

    public String genarateId(String type) {

        //            Connection connection = DBConnection.getInstance().getConnection();
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT ProductID FROM products ORDER BY ProductID DESC LIMIT 1");

        //System.out.println(type);

        String newId = type + "001"; // Default ID

        List<String> list =  productBo.id();

        System.out.println(list);

        if (!list.isEmpty()) {
            String lastId = list.get(0);
            int idNumber = Integer.parseInt(lastId.substring(1)) + 1;
            newId = String.format(type + "%03d", idNumber);
        }

//        if (list==null ) {
//            //String lastId = resultSet.getString("ProductID");
//            //int newId = Integer.parseInt(lastId.substring(1)) + 1;
//            //return String.format(type+"%03d", newId);
//        } else {
//            return type+"001"; // Default ID if no order exist
//        }
        System.out.println(newId);
        return newId;

    }

}
