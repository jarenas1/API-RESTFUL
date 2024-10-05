package com.crud.CRUD.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import javax.management.relation.Role;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 4, max = 12)
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank
    @Column(nullable = false)
    //PONEMOS QUE NO SE MUESTRE EN LAS RESPUESTAS PARA MÁS SEGURIDAD
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    //GENERAMOS RELACION CON LOS RELACIONES
    @JsonIgnoreProperties({"users"}) //VA A EVITAR CICLOS INFINITOS, CUANDO ENCUENTRE LA LLAVE users, LA VA A IGNORAR
    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"), //columna nuestra
            inverseJoinColumns = @JoinColumn(name = "role_id"),  //columna de la otra entidad,
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})}  //Indicamos columnas unicas
    )
    private List<RoleEntity> roles;

    //CHECK IF IS ADMIN
    @Transient //evita que se cree esta columna en la db
    private boolean admin;

    private boolean enabled; //verificar si esta habilitado se llenara con un prePersist

    @PrePersist //SETEAMOS EL ENABLED ANTES DE PERSISITR EL USUARIO
    public void prePersist(){
        this.enabled = true;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    public @NotBlank String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank String username) {
        this.username = username;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
