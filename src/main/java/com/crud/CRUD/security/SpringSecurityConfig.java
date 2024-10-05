package com.crud.CRUD.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    //CREAMOS UN COMPONENTE PARA ENCRIPTAR CONTRASEÑAS
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //MANEJO DE LAS RUTAS PUBLICAS Y ESO
//    @Bean  //va a captar el http security (automaticamente)
//    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception { //lanza excepcion
//        return httpSecurity.authorizeHttpRequests((auth)->auth
//                .requestMatchers("/api/users").permitAll() //permite todas las peticiones a la ruta /users PODEMOS AÑADIR MÁS
//                .anyRequest().authenticated()) //las demas rutas exige autenticacion
//                .csrf(config -> config.disable()) //esto es para trabajar con thymeleaf asi que se desactiva
//                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //se desactiva e manejo de la session por http ya que se manejara por JWT
//                .build();
//    }

    //LA DE ARRIBA SOLO LIBERABA UNA RUTA, EN ESTE LIBERAMOS VARIAS E INDICAMOS LOS METODOS QUE SE LIBERARAN
    @Bean  //va a captar el http security (automaticamente)
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception { //lanza excepcion
        return httpSecurity.authorizeHttpRequests((auth)->auth
                        //PSAMOS EL METODO Y LA RUTA
                        .requestMatchers(HttpMethod.GET,"/api/users").permitAll()
                        .requestMatchers(HttpMethod.POST,"api/users/register").permitAll()
                        .anyRequest().authenticated()) //resto de rutas privadas
                .csrf(config -> config.disable()) //esto es para trabajar con thymeleaf asi que se desactiva
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //se desactiva e manejo de la session por http ya que se manejara por JWT
                .build();
    }
}
