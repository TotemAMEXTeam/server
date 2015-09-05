package net.twilightstudios.amex.weather.service;

import java.io.IOException;
import java.util.List;

import net.twilightstudios.amex.weather.entidad.Forecast;

import org.json.JSONException;

public interface WeatherService {

	public Forecast retrieveForecast(String city) throws IOException, JSONException;
	
	public List<Forecast> retrieve5dayForecast(String city) throws IOException, JSONException;
}
