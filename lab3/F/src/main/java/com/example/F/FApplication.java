package com.example.F;  // Пакет должен соответствовать структуре

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FApplication {
	public static void main(String[] args) {
		SpringApplication.run(FApplication.class, args);
		System.out.println("Приложение запущено! Доступно на http://localhost:8080");
	}
}