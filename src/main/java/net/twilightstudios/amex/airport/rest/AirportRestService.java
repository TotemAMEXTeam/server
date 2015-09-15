package net.twilightstudios.amex.airport.rest;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;

import net.twilightstudios.amex.airport.service.AirportService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/airport")
public class AirportRestService {

	private AirportService airportService;

	
	@RequestMapping(value="/retrieveCity", method=RequestMethod.GET, produces={"application/json"})
	public @ResponseBody String retriebeCity(@RequestParam String name, @RequestParam String code) throws IOException {
		try {
			return airportService.getCityByAirport(name, code);
		} catch (Exception e) {
			 throw new WebApplicationException(e);
		}
	}
	
	
	// Getters y Setters
	
	public AirportService getAirportService() {
		return airportService;
	}

	public void setAirportService(AirportService airportService) {
		this.airportService = airportService;
	}
}
