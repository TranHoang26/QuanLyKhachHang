package org.example.demo.repository;

import java.util.List;

public interface IGenerateRepository<T>{
    List<T> findAll();

    T findById(Long id);

    void save(T t);

    void remove(T t);
}
