package net.twilightstudios.amex.places.rest;

import java.util.Map;

import net.twilightstudios.amex.places.entity.Coordinates;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CoordinatesConverter implements Converter<String, Coordinates> {

	@Override
	public Coordinates convert(String json) {

		ObjectMapper mapper = new ObjectMapper();
		//convert JSON string to Map
		try {
			Map <String, Double>map = mapper.readValue(json, new TypeReference<Map<String,Double>>(){});
			double lon = map.get("lon");
			double lat = map.get("lat");
			
			Coordinates result = new Coordinates(lat, lon);
			
			return result;
			
		} catch (Exception e){
			
			throw new IllegalArgumentException(json,e);
		}
	}
}
