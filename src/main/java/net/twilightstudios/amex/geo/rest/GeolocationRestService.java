package net.twilightstudios.amex.geo.rest;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;

import net.twilightstudios.amex.geo.service.GeolocationService;
import net.twilightstudios.amex.places.entity.Coordinates;

import org.json.JSONException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/geo")
public class GeolocationRestService {

	private GeolocationService geolocationService;

	public GeolocationService getGeolocationService() {
		return geolocationService;
	}

	public void setGeolocationService(GeolocationService geolocationService) {
		this.geolocationService = geolocationService;
	}
	
	@RequestMapping(value="/locate/{city}", method=RequestMethod.GET, produces={"application/json"})
	public Coordinates retrieveMap(@RequestParam(required=false) String country,@PathVariable String city) throws IOException, JSONException {
		try {
			return geolocationService.geolocateCity(city, country);			
		} catch (Exception e) {
			 throw new WebApplicationException(e);
		}
	}
	
}
