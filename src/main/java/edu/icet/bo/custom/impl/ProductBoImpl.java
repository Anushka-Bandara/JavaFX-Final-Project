package edu.icet.bo.custom.impl;

import edu.icet.bo.custom.ProductBo;
import edu.icet.dao.DaoFactory;
import edu.icet.dao.custom.ProductDao;
import edu.icet.entity.OrderEntity;
import edu.icet.entity.ProductEntity;
import edu.icet.model.Product;
import edu.icet.model.order.Order;
import edu.icet.util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;

import java.util.List;

public class ProductBoImpl implements ProductBo {

    private ProductDao productDao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);

    @Override
    public List<String> id() {
        return productDao.id();
    }

    @Override
    public boolean saveProduct(Product dto) {
        return productDao.save(new ModelMapper().map(dto,ProductEntity.class));
    }

    @Override
    public ObservableList<Product> getAllProduct() {
        List<ProductEntity> list = productDao.getAll();
        ObservableList<Product> products = FXCollections.observableArrayList();

        for(ProductEntity entity : list){
            Product dto = new ModelMapper().map(entity, Product.class);
            products.add(dto);
        }

        return products;
    }

    @Override
    public void getSelectProduct() {

    }

    @Override
    public boolean updateProduct(Product dto) {
        return productDao.update(new ModelMapper().map(dto, ProductEntity.class));
    }

    @Override
    public boolean deleteProduct(String id) {
        return productDao.delete(id);
    }

    @Override
    public boolean updateStock(String productId, Integer qty) {
        return productDao.updateStock(productId,qty);
    }
}
