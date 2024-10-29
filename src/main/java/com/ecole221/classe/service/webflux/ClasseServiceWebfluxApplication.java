package com.ecole221.classe.service.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication(scanBasePackages = "com.ecole221.classe.service.webflux")
@EnableR2dbcRepositories(basePackages = "com.ecole221.classe.service.webflux")
public class ClasseServiceWebfluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClasseServiceWebfluxApplication.class, args);
	}

}
