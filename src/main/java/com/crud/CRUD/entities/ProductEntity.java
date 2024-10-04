package com.crud.CRUD.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "products")
public class ProductEntity {

    //AÑADIREMOS TODO EL TEMA DE VALIDACIONES, ESTE VA EN CONJUNDO CON @Valid EN LOS CONTROLADORES
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //ESTE MENSAJE VIENE DE UN PROPERTIES Y SE PASA AL MAIN
    @NotEmpty(message = "{NotEmpty.product.name}") //Valida que no este vacio
    @Size(min=3, max = 20) //MUY UTIL PARA USERNAMES Y ESO, Valida tamaño maximo y minimo
    private String name;

    @NotBlank
    private String description;

    @Min(500) //damos un valor minimo
    private Long price;

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDescription() {
        return  description;
    }

    public void setDescription(String description) {
        this. description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "description='" +  description + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
