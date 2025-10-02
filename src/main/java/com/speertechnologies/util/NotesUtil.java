package com.speertechnologies.util;

import org.springframework.http.converter.json.MappingJacksonValue;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class NotesUtil {

	
	public static  MappingJacksonValue filterateFields(String filterName, Object toFilter, String... includeFields)
	{
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(toFilter);
		
		SimpleBeanPropertyFilter propertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(includeFields);
		
		FilterProvider filter = new SimpleFilterProvider().addFilter(filterName, propertyFilter);
		
		mappingJacksonValue.setFilters(filter);

		return mappingJacksonValue;
	}
	

	
}
