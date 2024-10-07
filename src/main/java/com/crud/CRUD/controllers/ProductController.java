package com.crud.CRUD.controllers;

import com.crud.CRUD.entities.ProductEntity;
import com.crud.CRUD.services.MailService;
import com.crud.CRUD.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService service;

    //INYECTAMOS EL EMAIL
    @Autowired
    private MailService mailService;

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
    public  ResponseEntity<?> create (@Valid @RequestBody ProductEntity productEntity, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }
        ProductEntity product = service.save(productEntity);
        //ENVIAMOS EL EMAIL
        mailService.email("juanjoarenas1218@gmail.com", "JUANITO JOSE ARENAS", "SE HA CREADO UN NUEVO PRODUCTO MI PA LLAMADO %s");
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    private ResponseEntity<?> validation(BindingResult result) {
        //CREACION DEL ERROR
        Map<String, String> errors = new HashMap<>();
        //Iteramos errores para añadirlos al map
        result.getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), "El campo "+fieldError.getField()+" "+ fieldError.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    //AÑADMOS VALID, EL CUAL PERMITE VALIDAR LOS CAMPOS QUE ESTAMOS ENVIANDO DESDE LA CLASE ENTITY

    @PutMapping("/{id}") //BINDINGRESULT SE PONE AL LADO DEL OBJETO QUE SE ESTA VALIDANDO, Y ESTE CONTIENE TODOS LOS ERRORES OBTENIDOS DE LAS VALIDACIONES
    public ResponseEntity<?> update (@PathVariable Long id, @Valid @RequestBody  ProductEntity product, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }
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
