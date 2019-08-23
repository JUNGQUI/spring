package com.jk.spring;

import com.jk.spring.service.UseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Application {

    private final UseService useService;

    @Autowired
    public Application(UseService useService) {
        this.useService = useService;
    }

    private static UseService localUseService;

    @PostConstruct
    private void configure() {
        localUseService = this.useService;
    }

    public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		localUseService.useInterface();
		localUseService.useStartegy();
	}

}
