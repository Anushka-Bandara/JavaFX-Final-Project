package edu.icet.dao.custom;

import edu.icet.dao.CrudDao;
import edu.icet.entity.ProductEntity;

public interface ProductDao extends CrudDao<ProductEntity> {
    boolean updateStock(String productId, Integer qty);
}
