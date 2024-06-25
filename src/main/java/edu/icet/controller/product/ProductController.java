package edu.icet.controller.product;

import edu.icet.db.DBConnection;
import edu.icet.model.Product;
import edu.icet.util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ProductController {
    private static ProductController instance;

    private ProductController(){}

    public static ProductController getInstance(){
        if(instance==null){
            return instance = new ProductController();
        }
        return instance;
    }

    public ObservableList<Product> getAllProducts() throws RuntimeException {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM products");
            ObservableList<Product> listTable = FXCollections.observableArrayList();
            while (resultSet.next()) {
                listTable.add(
                        new Product(
                                resultSet.getString("ProductID"),
                                resultSet.getString("Name"),
                                resultSet.getString("Size"),
                                resultSet.getString("Category"),
                                resultSet.getDouble("Price"),
                                resultSet.getInt("Qty"),
                                resultSet.getString("imagePath")
                        )
                );
            }
            return listTable;

        }  catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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

    public Boolean saveProduct(Product product) throws SQLException, ClassNotFoundException {

        if(product.getImagePath()==null && product.getName()==null){
            return false;
        }
        String sql = "INSERT INTO products (" +
                "ProductID, Name, Size, Category, Price, Qty, ImagePath) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Boolean isAdd = CrudUtil.execute(
                sql, product.getProductId(),
                product.getName(),
                product.getSize(),
                product.getCategory(),
                product.getPrice(),
                product.getQty(),
                product.getImagePath());

        return isAdd;
    }

    public boolean updateProduct(Product product) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Products SET Name = ?, Size = ?, Category = ?, Price = ?, Qty = ?, ImagePath = ? WHERE ProductID = ?";
        return CrudUtil.execute(sql, product.getName(), product.getSize(), product.getCategory(), product.getPrice(), product.getQty(), product.getImagePath(), product.getProductId());
    }

    public boolean stockManagment(String productId,Integer Qty) throws SQLException, ClassNotFoundException {
        String sql ="UPDATE Products SET Qty= ? WHERE ProductID = ?";
        return CrudUtil.execute(sql,Qty,productId);
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

    public boolean deleteProduct(String productId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Products WHERE ProductID = ?";
        return CrudUtil.execute(sql, productId);
    }

    public String genarateId(String type) {

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ProductID FROM products ORDER BY ProductID DESC LIMIT 1");


            if (resultSet.next()) {
                String lastId = resultSet.getString("ProductID");
                int newId = Integer.parseInt(lastId.substring(1)) + 1;
                return String.format(type+"%03d", newId);
            } else {
                return type+"001"; // Default ID if no order exist
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return type+"001"; // Fallback ID
        }

    }
}
