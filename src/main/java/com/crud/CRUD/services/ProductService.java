package com.crud.CRUD.services;

import com.crud.CRUD.entities.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    //Metodos que se van a implementae

    List<ProductEntity> findAll();

    Optional<ProductEntity> findById(Long id);

    ProductEntity save(ProductEntity productEntity);

    Optional<ProductEntity> delete(Long id);

    Optional<ProductEntity> update(ProductEntity productEntity);

}
