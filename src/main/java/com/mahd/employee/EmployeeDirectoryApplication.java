package com.mahd.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mahd.employee.models.SpringSecurityAuditor;
import org.springframework.web.bind.annotation.CrossOrigin;

@EnableJpaAuditing(auditorAwareRef="auditorAware")
@SpringBootApplication
@CrossOrigin(origins = "*")
public class EmployeeDirectoryApplication {
	
	@Bean
	public AuditorAware<String> auditorAware() {
		return new SpringSecurityAuditor();
	}

	public static void main(String[] args) {
		SpringApplication.run(EmployeeDirectoryApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
