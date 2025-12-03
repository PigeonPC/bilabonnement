package com.example.bilabonnement.forms;

import java.util.List;

public interface BaseFormRepository<T> {


        T findById(Integer id);

        void deleteById(Integer id);

        List<T> findAll();

        void save(T entity);
    }
