package com.crud.CRUD.controllers;

import com.crud.CRUD.entities.ProductEntity;
import com.crud.CRUD.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping
    public List<ProductEntity> finAll(){
        return service.findAll();
    }
    @GetMapping("/{id}") //recibe el id por el navegador y retorna una respuesta buena o mala
    public ResponseEntity<?> view (@PathVariable  Long id){
        Optional<ProductEntity> optionalProduct = service.findById(id);
        if (optionalProduct.isPresent()){
            return ResponseEntity.ok(optionalProduct.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public  ResponseEntity<ProductEntity> create (@Valid @RequestBody ProductEntity productEntity){
        ProductEntity product = service.save(productEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    //AÑADMOS VALID, EL CUAL PERMITE VALIDAR LOS CAMPOS QUE ESTAMOS ENVIANDO DESDE LA CLASE ENTITY

    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> update ( @PathVariable Long id, @Valid @RequestBody  ProductEntity product){
        Optional<ProductEntity> productEntityOptional = service.update(product);
        if (productEntityOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(productEntityOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}") //recibe el id por el navegador y retorna una respuesta buena o mala
    public ResponseEntity<?> delete (@PathVariable  Long id){
        Optional<ProductEntity> optionalProduct = service.delete(id);
        if (optionalProduct.isPresent()){
            return ResponseEntity.ok(optionalProduct.get());
        }
        return ResponseEntity.notFound().build();
    }

}
