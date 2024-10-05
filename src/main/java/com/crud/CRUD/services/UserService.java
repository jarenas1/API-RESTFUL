package com.crud.CRUD.services;

import com.crud.CRUD.entities.UserEntity;

import java.util.List;

public interface UserService {

    List<UserEntity> findAll();

    UserEntity save (UserEntity userEntity);
}
