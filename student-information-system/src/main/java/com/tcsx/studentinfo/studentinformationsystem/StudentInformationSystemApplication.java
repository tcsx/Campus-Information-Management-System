package com.tcsx.studentinfo.studentinformationsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class StudentInformationSystemApplication extends SpringBootServletInitializer{

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(StudentInformationSystemApplication.class);
    }
	
	
	public static void main(String[] args) {
		SpringApplication.run(StudentInformationSystemApplication.class, args);
	}
}
