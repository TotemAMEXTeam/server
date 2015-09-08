package net.twilightstudios.amex.weather.rest;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;

import net.twilightstudios.amex.weather.entidad.Forecast;
import net.twilightstudios.amex.weather.service.WeatherService;

import org.json.JSONException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherRestService {
	
	private WeatherService weatherService;
	
	
	
	public WeatherService getWeatherService() {
		return weatherService;
	}



	public void setWeatherService(WeatherService weatherService) {
		this.weatherService = weatherService;
	}



	@RequestMapping(value="/retrieveForecast/{city}", method=RequestMethod.GET, produces={"application/json"})
	public Forecast retrieveForecast(@PathVariable String city) throws IOException, JSONException {
		try {
			return weatherService.retrieveForecast(city);
		} catch (Exception e) {
			 throw new WebApplicationException(e);
		}
	}

}
