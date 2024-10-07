package com.crud.CRUD.controllers;

import com.crud.CRUD.entities.UserEntity;
import com.crud.CRUD.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//IMPLEMENTACION DE LOS CORS
@CrossOrigin(originPatterns = "*") //TODAS LAS RUTAS DE FRONT TIENEN ACCESO
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserEntity> list(){
        return userService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid  @RequestBody UserEntity userEntity, BindingResult result){
        //REVISAR VALIDACIONES
        if(result.hasFieldErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userEntity));
    }

    //CREAREMOS UN REGISTRO QUE NO PERMITA CREAR ADMINS, PARA QUE SEA DE ACCESO PUBLICO Y EL DE ARRIBA PRIVADO

    @PostMapping("/register")
    public ResponseEntity<?> registrer(@Valid  @RequestBody UserEntity userEntity, BindingResult result){
        //REVISAR VALIDACIONES
        if(result.hasFieldErrors()){
            return validation(result);
        }
        //PONEMOS EN FALSO EL IS ADMIN PARA QUE NO SE PUEDAN CREAR
        userEntity.setAdmin(false);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userEntity));
    }

    //VALIDACION
    private ResponseEntity<?> validation(BindingResult result) {
        //CREACION DEL ERROR
        Map<String, String> errors = new HashMap<>();
        //Iteramos errores para añadirlos al map
        result.getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), "El campo "+fieldError.getField()+" "+ fieldError.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    //AÑADMOS VALID, EL CUAL PERMITE VALIDAR LOS CAMPOS QUE E

}
