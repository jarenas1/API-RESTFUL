package com.crud.CRUD.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String name;

    //indicamos la relacion bidirecciona    //ESTOS OTROS 2, SON NECESARIOS PARA VEITAR ERRORES
    @JsonIgnoreProperties({"roles", "handler", "hibernateLazyInitializer"}) //VA A EVITAR CICLOS INFINITOS, CUANDO ENCUENTRE LA LLAVE users, LA VA A IGNORAR
    @ManyToMany(mappedBy = "roles")
    List<UserEntity> users = new ArrayList<>();

    public RoleEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }
}
