package com.crud.CRUD.services;

import com.crud.CRUD.entities.ProductEntity;

import com.crud.CRUD.respositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//IMPLEMENTAMOS TODOS LOS METODOS DE JPA EN CADA METODO DEL SERVICIO
import java.util.List;
import java.util.Optional;

@Service
public class ProductImplementation implements ProductService{ //LLAMAMOS EL CONTRATO

    @Autowired
    private ProductRepository productRepository;   //llamamos al repo para acceder a sus metodos de bases de datos

    @Transactional(readOnly= true) //todos son transaccionales
    @Override
    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly= true)
    @Override
    public Optional<ProductEntity> findById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    @Override
    public ProductEntity save(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    @Transactional
    @Override
    public Optional<ProductEntity> delete(ProductEntity productEntity) {
        //TRAEMOS EL OBJETO POR ID
        Optional<ProductEntity> productEntityOptional = productRepository.findById(productEntity.getId());

        //VERIFICAMOS SI EXISTR
        productEntityOptional.ifPresentOrElse(productEntity1 -> {
            productRepository.delete(productEntity);
        }, ()->{});
        return productEntityOptional;
    }
}
