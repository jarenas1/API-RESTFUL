package com.crud.CRUD.services;

import com.crud.CRUD.entities.RoleEntity;
import com.crud.CRUD.entities.UserEntity;
import com.crud.CRUD.respositories.RoleRepository;
import com.crud.CRUD.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserImplementation implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository; //PARA PODER TRAER EL ROL CON EL METODO PERSONALIZADO

    @Autowired
    private PasswordEncoder passwordEncoder; //PARA ENCRIPTAR LA CONTRASEÑA

    @Override
    @Transactional(readOnly = true)
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public UserEntity save(UserEntity userEntity) {
        //ASIGNAMOS EL ROL USER
        Optional<RoleEntity> optionalRoleEntity = roleRepository.findByName("ROLE_USER");
        //LISTA DE ROLES QUE CONTIENE EL USUARIO
        List<RoleEntity> roles = new ArrayList<>();
        //SI EL ROL EXISTE, SE LO AÑADIMOS A LOS ROLES DEL USUARIO
        optionalRoleEntity.ifPresent(role ->{
            roles.add(role);
        });
        //VERIFICAMOS SI EL ATRIBUTO ADMINS ES TRUE PARA ASIGNARLO
        if (userEntity.isAdmin()){
            //llamamos al rol
            Optional<RoleEntity> optionalRoleEntityAdmin = roleRepository.findByName("ROLE_ADMIN");
            //lo añadimos a la lista pero con una opcion más limpia, el infiere que lo que se esta pasando es lo que se añadira
            optionalRoleEntityAdmin.ifPresent(roles::add); //hace add a la lista con lo que traiga el optional
        }
        //SETEAMOS ROLES AL USUARIO
        userEntity.setRoles(roles);
        //ENCRIPTAMOS CONTRASEÑA Y SETEAMOS
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }
}
