package edu.icet.dao;

import edu.icet.entity.OrderEntity;
import javafx.collections.ObservableList;

import java.util.List;

public interface CrudDao <T> extends SuperDao{

    List<String> id();
    boolean save(T entity);
    List<T> getAll();
    boolean update(T entity);
    boolean delete(String id);
}
