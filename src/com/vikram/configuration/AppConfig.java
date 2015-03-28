package com.vikram.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vikram.model.Temp;

@Configuration
public class AppConfig {
	
	@Bean
	public Temp getTemp(){
		return new Temp("first","last");
	}
	
}
