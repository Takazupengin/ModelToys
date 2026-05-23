package com.example.ms_inventario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient; // <- Importante

@SpringBootApplication
@EnableDiscoveryClient // <- Esta anotación le dice al microservicio: "Regístrate en Eureka"
public class MsInventarioApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsInventarioApplication.class, args);
	}
}