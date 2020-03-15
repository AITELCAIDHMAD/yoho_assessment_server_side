	package com.yoho;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.yoho.entities.AppRole;
import com.yoho.entities.AppUser;
import com.yoho.service.AccountService;

@SpringBootApplication
public class YohoAssessmentSpringApplication extends SpringBootServletInitializer implements CommandLineRunner {

	@Autowired
	private AccountService accountService;
	
	private static final Logger logger = LoggerFactory.getLogger(YohoAssessmentSpringApplication.class);

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(YohoAssessmentSpringApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(YohoAssessmentSpringApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void run(String... arg0) throws Exception {
		logger.info("STARTING  APP");
		
		AppUser user=accountService.findUserByUsername("hmad");
		
		if(user==null) {
			accountService.saveRole(new AppRole(null,"ADMIN"));
			accountService.saveRole(new AppRole(null,"USER"));

			accountService.saveUser(new AppUser(null,"hmad", "123", null));
			accountService.addRoleToUser("hmad", "ADMIN");
		}
		
		
	}
}