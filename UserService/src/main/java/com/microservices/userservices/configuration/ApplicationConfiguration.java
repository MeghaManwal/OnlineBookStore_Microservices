package com.microservices.userservices.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationConfiguration {

	@Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder(){
             return new BCryptPasswordEncoder();
        }
}
