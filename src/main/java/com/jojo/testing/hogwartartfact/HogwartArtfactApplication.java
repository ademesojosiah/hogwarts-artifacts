package com.jojo.testing.hogwartartfact;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HogwartArtfactApplication {

	public static void main(String[] args) {
		SpringApplication.run(HogwartArtfactApplication.class, args);
	}
	List<String> data = new ArrayList<String>();


}
