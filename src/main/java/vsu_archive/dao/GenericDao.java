package vsu_archive.dao;

import java.util.List;

public interface GenericDao<T extends MyObject> {
    T getObjectById(Integer key);
    T insert(T object);
    T insertNew();
    boolean update(T object);
    boolean delete(T object);
    List<T> selectAll();
}
