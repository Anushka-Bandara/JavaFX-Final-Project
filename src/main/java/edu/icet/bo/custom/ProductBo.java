package edu.icet.bo.custom;

import edu.icet.bo.SuperBo;
import edu.icet.entity.ProductEntity;
import edu.icet.model.Product;
import javafx.collections.ObservableList;

import java.util.List;

public interface ProductBo extends SuperBo {

    List<String> id();
     boolean saveProduct(Product dto);

     ObservableList<Product> getAllProduct();

     void getSelectProduct();

     boolean updateProduct(Product dto);

     boolean deleteProduct(String id);

    boolean updateStock(String productId, Integer qty);
}
