package com.crud.CRUD.respositories;

import com.crud.CRUD.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    //METODO PARA BUSCAR POR USERNAME
    Optional<UserEntity> findByUsername(String username);
}
