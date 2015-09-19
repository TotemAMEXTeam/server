package net.twilightstudios.amex.airport.service.impl;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.twilightstudios.amex.airport.service.AirportService;

public class SmartAirportService implements AirportService {

	private Map<String,String> exceptions;
	
	@Override
	public String getCityByAirport(String name, String code) {

		if(exceptions.containsKey(code)){
		
			return exceptions.get(code);
		}
		
		char[] separators = {'-','/','('};
				
		for(char separator:separators){
			
			int index = name.indexOf(separator);
			
			if(index != -1){
				
				return smartTrim(name.substring(0, index));				
			}
		}
		
		return name;
	}
	
	private String smartTrim(String name){
		
		String result;
		
		String first = name.trim();
		
		Pattern pattern = Pattern.compile("(.*)\\s+intl$", Pattern.CASE_INSENSITIVE);		
		Matcher matcher = pattern.matcher(first);
		
		if(matcher.matches()){
			
			result = matcher.group(1);
		}
		else{
			
			result = first;
		}
		
		return result;
	}

	// Getter & setter
	
	public Map<String, String> getExceptions() {
		return exceptions;
	}

	public void setExceptions(Map<String, String> exceptions) {
		this.exceptions = exceptions;
	}	
}
