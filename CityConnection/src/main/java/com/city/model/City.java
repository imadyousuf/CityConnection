package com.city.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class City {
	
	private String name;
	
	private Set<City> neighbourCities = new HashSet<>();

	public City(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<City> getNeighbourCities() {
		return this.neighbourCities;
	}

	public void addNeighbourCity(City neighbourCity) {
		this.neighbourCities.add(neighbourCity);
	}
    
    @Override
    public boolean equals(Object o) {
        return Objects.equals(name, ((City) o).name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
	
}
