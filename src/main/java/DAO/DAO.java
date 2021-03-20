package DAO;

import java.util.Collection;

public interface DAO<T> {
    void add(T entity);
    void update(T entity);
    T getById(int id);
    Collection<T> getAll();
    void delete(T entity);
}
