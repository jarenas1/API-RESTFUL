package com.crud.CRUD.services;

import com.crud.CRUD.entities.UserEntity;
import com.crud.CRUD.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JpaUserDetailsService implements UserDetailsService {  //implementamos la clase que nos ayudara con la validacion

    @Autowired
    private UserRepository userRepository;   //INYECTAMOS PARA PODER BUSCAR AL USUARIO DEBEMOS CREAR EL METOFO QUE BUSQUE POR USERNAMEE

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //captamos el username y lo buscamos con nuestro metodo
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);

        //VERIFICAMOS SI EXISTIO O NO
        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException(String.format("Username %s no existe en el sistema", username));
        }

        UserEntity userEntity = userOptional.get();

        //creamos una lista de roles, por medio del apiStream para transormarlo
        List<GrantedAuthority> authorities = userEntity.getRoles().stream()
                .map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getName())).collect(Collectors.toList());

        //retornamos una instancia de User, la cual es de SPRING SECURITY, la nuestra se llama UserEntity
        return new org.springframework.security.core.userdetails.User(userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                true,
                true,
                true,
                authorities);
        //PASAMOS TODOS LOS ATRIBUTOS, 3 TRUE Y LA LISTA CREADA ANTERIORMENTE
    }
}
