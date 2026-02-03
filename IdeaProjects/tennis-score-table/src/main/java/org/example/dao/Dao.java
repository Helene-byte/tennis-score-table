package org.example.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T, ID> {

    T save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();

    List<T> findAll(int offset, int limit);
    int countAll();
}
