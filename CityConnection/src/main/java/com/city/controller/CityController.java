package com.city.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.city.service.ConnectionService;

@RestController
public class CityController {

	@Autowired
	ConnectionService service;
	
	public CityController() {
		// TODO Auto-generated constructor stub
	}
	
	@GetMapping("/connected")
	public String getConnected(@RequestParam("origin") String originCity, 
			@RequestParam("destination") String destinationCity) {
		return service.processConnectedCities(originCity, destinationCity);
	}

}
