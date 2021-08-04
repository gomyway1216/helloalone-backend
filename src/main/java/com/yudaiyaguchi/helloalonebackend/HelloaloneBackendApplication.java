package com.yudaiyaguchi.helloalonebackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.yudaiyaguchi.helloalonebackend.firebase.TaskService;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.yudaiyaguchi.helloalonebackend")
public class HelloaloneBackendApplication {
	
	@Autowired
	TaskService taskService;
	

	public static void main(String[] args) {
		SpringApplication.run(HelloaloneBackendApplication.class, args);
	}

}
