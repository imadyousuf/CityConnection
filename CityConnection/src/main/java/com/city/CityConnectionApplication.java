package com.city;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.city.service.ConnectionService;

@SpringBootApplication
public class CityConnectionApplication {
	
	static Logger log = Logger.getLogger(CityConnectionApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(CityConnectionApplication.class, args);
		BasicConfigurator.configure();
		log.info("CityConnection Started.....");
	}

}
