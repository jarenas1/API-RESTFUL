package com.crud.CRUD;

import com.crud.CRUD.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@PropertySource("classpath:messages.properties") //LOS MENSAJES QUE LANZARAN LAS VALIDACIONES VENDRAN DE ACÁ
public class CrudApplication {


	public static void main(String[] args) {
		SpringApplication.run(CrudApplication.class, args);
	}

}
