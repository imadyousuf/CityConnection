package com.city.service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.city.model.City;

@Service
public class ConnectionService implements IConnectionService{
	
	Logger log = Logger.getLogger(ConnectionService.class.getName());
	
    @Value("classpath:city.txt")
    private Resource resource;

	public ConnectionService() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String processConnectedCities(String originCity, String destinationCity) {
		String isCitiesConnected = "No";
		Map<String, City> cityMap = new HashMap<>();
		try {
			log.debug("Origin City: "+originCity+" === Destination City: "+destinationCity);
			cityMap = getCityMapFromResource();
			System.out.println(cityMap);
			if(isConnectedCities(originCity, destinationCity, cityMap)) {
				isCitiesConnected = "Yes";
			}
		}catch(Exception e) {
			log.error(e);
			isCitiesConnected = "No"; 
		}
		
		log.debug("isCitiesConnected == " + isCitiesConnected);
		return isCitiesConnected;//"Origin City: "+originCity+" === Destination City: "+destinationCity;
	}
	
	public Map<String, City> getCityMapFromResource() throws Exception{
		Map<String, City> cityMap = new HashMap<>();
		try {
			Stream<String> lines = Files.lines(Paths.get(resource.getURI()),StandardCharsets.UTF_8);
			//cityMap = lines.map(cityLine -> cityLine.split(","))
			//			   .collect(Collectors.groupingBy(cityLine -> cityLine[0].trim().toUpperCase(),Collectors.mapping(cityLine -> cityLine[1].trim().toUpperCase(), Collectors.toList())));
			lines.forEach(cityLine -> {
				String[] cityArray = cityLine.split(",");
				if(cityArray!=null && cityArray.length>0) {
					City city1 = cityMap.getOrDefault(cityArray[0].toUpperCase().trim(), new City(cityArray[0].trim()));
					City city2 = cityMap.getOrDefault(cityArray[1].toUpperCase().trim(), new City(cityArray[1].trim()));
					city1.addNeighbourCity(city2);
					city2.addNeighbourCity(city1);
					cityMap.put(city1.getName().toUpperCase(), city1);
					cityMap.put(city2.getName().toUpperCase(), city2);
				}
			});
				
		} catch (Exception exception) {
			log.error(exception);
			throw exception;
		}
		return cityMap;
	}
	
	public String getOriginCityFromMapValues(String originCity, Iterator<List<String>> destinationCitiesItr) {
		while(destinationCitiesItr.hasNext()) {
			List<String> cityList = destinationCitiesItr.next();
			for(String city : cityList) {
				if(originCity.toUpperCase().equals(city)) {
					return city;
				}
			}
		}
		return null;
	}
	
	public boolean isConnectedCities(String originCity, String destinationCity, Map<String, City> cityMap) throws Exception {	
		log.debug("isConnectedCities Method Start .....");
		Objects.requireNonNull(originCity);
		Objects.requireNonNull(destinationCity);
		if(originCity.equalsIgnoreCase(destinationCity)) {
			return true;
		}
		City city1 = cityMap.get(originCity.toUpperCase());
		City city2 = cityMap.get(destinationCity.toUpperCase());
		
		if(city1.getNeighbourCities().contains(city2)) {
			return true;
		}
        Set<City> vCities = new HashSet<>(Collections.singleton(city1));
        Deque<City> citieslist = new ArrayDeque<>(city1.getNeighbourCities());
        while (!citieslist.isEmpty()) {
            City city = citieslist.getLast();
            if (city.equals(city2)) return true;
            if (!vCities.contains(city)) {
                vCities.add(city);
                citieslist.addAll(city.getNeighbourCities());
                citieslist.removeAll(vCities);
            } else {
            	citieslist.removeAll(Collections.singleton(city));
            }
        }
        return false;
	}

}
