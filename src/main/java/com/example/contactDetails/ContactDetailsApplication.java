package com.example.contactDetails;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class ContactDetailsApplication {

	public static void main(String[] args) {
		System.out.println(">>> JVM Default Timezone = " + TimeZone.getDefault());
		SpringApplication.run(ContactDetailsApplication.class, args);
	}

}
