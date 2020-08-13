package com.city.services;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.city.service.ConnectionService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConnectionServiceTest {
	
	@Autowired
	private ConnectionService service;

	public ConnectionServiceTest() {
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void testSameCityConnection() {
		String originCity = "Boston";
		String destinationCity = "Boston";
		assertTrue(service.processConnectedCities(originCity, destinationCity).equalsIgnoreCase("Yes"));
	}

	@Test
	public void testCityConnectionWithUnConnectedCities() {
		String originCity = "Boston";
		String destinationCity = "Alpharetta";
		assertTrue(service.processConnectedCities(originCity, destinationCity).equalsIgnoreCase("No"));
	}
}
