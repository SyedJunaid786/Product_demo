package com.layers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductDemoApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(ProductDemoApplication.class, args);
		System.out.println("In Master Branch");
		System.out.println("New Feature 23 Added");
		System.out.println("New Feature 24 Added");
	}

}
