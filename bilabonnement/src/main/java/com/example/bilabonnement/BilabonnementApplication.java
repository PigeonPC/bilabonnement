package com.example.bilabonnement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//Bruges midlertidigt til at eksludere databasen
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

// Fjern exclude når databasen er oprettet
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BilabonnementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BilabonnementApplication.class, args);
	}

}
