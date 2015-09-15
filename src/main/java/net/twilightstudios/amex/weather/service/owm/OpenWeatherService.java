package net.twilightstudios.amex.weather.service.owm;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import net.twilightstudios.amex.util.rest.ApiKeyProvider;
import net.twilightstudios.amex.util.rest.RestProvider;
import net.twilightstudios.amex.weather.entity.Forecast;
import net.twilightstudios.amex.weather.entity.Precipitation;
import net.twilightstudios.amex.weather.entity.Summary;
import net.twilightstudios.amex.weather.entity.Temperature;
import net.twilightstudios.amex.weather.entity.Wind;
import net.twilightstudios.amex.weather.service.WeatherService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OpenWeatherService implements WeatherService {

	private final String appKey;
	
	private String language;
	private String url;
	private String urlForecast; 
	
	private RestProvider restProvider;
	
	public OpenWeatherService(ApiKeyProvider apiKeyProvider) {
	
		this.appKey = apiKeyProvider.getApiKey();
		
	}

	@Override
	public Forecast retrieveForecast(String city) throws IOException, JSONException {

		StringBuilder urlString = new StringBuilder(url);
		urlString.append("?q=");
		urlString.append(URLEncoder.encode(city, "UTF-8"));
		urlString.append("&lang=");
		urlString.append(language);
		urlString.append("&APPID=");
		urlString.append(appKey);
		
        String genreJson = restProvider.retrieveRawInformation(urlString.toString());
        
        JSONObject json = new JSONObject(genreJson);
        
        Forecast forecast = buildForecast(json);
        forecast.setCity(city);
        forecast.setTimestamp(new Date());
        
        return forecast;
	}

	@Override
	public List<Forecast> retrieve5dayForecast(String city) throws IOException,
			JSONException {
	
		StringBuilder urlString = new StringBuilder(urlForecast);
		urlString.append("?q=");
		urlString.append(URLEncoder.encode(city, "UTF-8"));
		urlString.append("&lang=");
		urlString.append(language);
		urlString.append("&APPID=");
		urlString.append(appKey);
		
        String genreJson = restProvider.retrieveRawInformation(urlString.toString());
       
        
        JSONObject json = new JSONObject(genreJson);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONArray list = json.getJSONArray("list");
        List<Forecast> resultado = new ArrayList<Forecast>(list.length());
        int lastDay = 0;
        List<Forecast> dayForecasts = new LinkedList<Forecast>();
        for(int i=0;i<list.length();i++){
        	
        	JSONObject obj = list.getJSONObject(i);
        	Forecast forecast = buildForecast(obj);
        	forecast.setCity(city);
        	
        	Date date;
        	Calendar calendar = Calendar.getInstance();
        	
        	try{
        		date = df.parse(obj.getString("dt_txt"));
        		calendar.setTime(date);
        	}
        	catch(ParseException e){
        		
        		calendar.add(Calendar.DAY_OF_MONTH, i);
        		date = calendar.getTime();
        	}
        	
        	forecast.setTimestamp(date);
        	
        	if(lastDay != calendar.get(Calendar.DAY_OF_MONTH)){
        		
        		lastDay = calendar.get(Calendar.DAY_OF_MONTH);
        		
        		if(!dayForecasts.isEmpty()){
        			
        			resultado.add(groupForecastList(dayForecasts));
        			dayForecasts = new LinkedList<Forecast>();
        		}
        	}
        	
        	dayForecasts.add(forecast);
        }
        
        if(!dayForecasts.isEmpty()){
			
			resultado.add(groupForecastList(dayForecasts));
		}
        
        return resultado;
	}
	
	private Forecast groupForecastList(List<Forecast> forecasts){
		
		if(forecasts.isEmpty()){
		
			return null;
		}
		
		Forecast resultado = new Forecast();
		Forecast referencia = null;
		
		Precipitation precipitation = new Precipitation();
		precipitation.setRain(0);
		precipitation.setSnow(0);
		
		Temperature temperature = new Temperature();
		temperature.setMin(Double.MAX_VALUE);
		temperature.setMax(Double.MIN_VALUE);
		
		for(Forecast forecast:forecasts){
			
			if(referencia == null){
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(forecast.getTimestamp());
				
				if(calendar.get(Calendar.HOUR_OF_DAY) == 12){
					
					referencia = forecast;
				}
			}
			
			if(forecast.getTemperature().getMin() < temperature.getMin()){
				
				temperature.setMin(forecast.getTemperature().getMin());
			}
			
			if(forecast.getTemperature().getMax() > temperature.getMax()){
				
				temperature.setMax(forecast.getTemperature().getMax());
			}
			
			precipitation.setRain(precipitation.getRain() + forecast.getPrecipitation().getRain());			
			precipitation.setSnow(precipitation.getSnow() + forecast.getPrecipitation().getSnow());
		}
		
		if(referencia == null){
		
			referencia = forecasts.get(0);
		}
		
		resultado.setCity(referencia.getCity());
		resultado.setCloudiness(referencia.getCloudiness());
		resultado.setHumidity(referencia.getHumidity());
		resultado.setPressure(referencia.getPressure());
		resultado.setSummary(referencia.getSummary());
		resultado.setTimestamp(referencia.getTimestamp());
		resultado.setWind(referencia.getWind());

		temperature.setActual(referencia.getTemperature().getActual());
		
		resultado.setPrecipitation(precipitation);
		resultado.setTemperature(temperature);
		
		return resultado;
	}
	
	private Forecast buildForecast(JSONObject json) throws JSONException{
		
		Forecast forecast = new Forecast();        
        
        JSONObject main = json.getJSONObject("main");
        
        Temperature temp = new Temperature();
        temp.setActual(convertKelvinToCelsius(main.getDouble("temp")));
        temp.setMin(convertKelvinToCelsius(main.getDouble("temp_min")));
        temp.setMax(convertKelvinToCelsius(main.getDouble("temp_max")));
        
        forecast.setTemperature(temp);
        
        forecast.setPressure(main.getDouble("pressure"));
        forecast.setHumidity(main.getDouble("humidity"));
        
        JSONObject wind = json.getJSONObject("wind");
        
        Wind windObj = new Wind();
        
        windObj.setSpeed(convertMphToKph( wind.getDouble("speed")));
        
        if(wind.has("deg")){
        	windObj.setHeading(wind.getDouble("deg"));
        }
        
        forecast.setWind(windObj);
        
        JSONObject clouds = json.getJSONObject("clouds");
        forecast.setCloudiness(clouds.getInt("all"));

        JSONObject weather = json.getJSONArray("weather").getJSONObject(0);
        
        Summary summary = new Summary();
        summary.setId(weather.getInt("id"));
        summary.setGroup(weather.getString("main"));
        summary.setDescription(weather.getString("description"));
        summary.setIcon(weather.getString("icon"));
        
        forecast.setSummary(summary);
        
        Precipitation precipitation = new Precipitation();
                
        if(json.has("rain")){

        	JSONObject rain = json.getJSONObject("rain");

        	if(rain.has("3h")){
        		precipitation.setRain(rain.getDouble("3h"));
        	}
        	else{
        		
        		precipitation.setRain(0);
        	}
        }
        else{
        	
        	precipitation.setRain(0);
        }
                
        if(json.has("snow")){
        	
        	JSONObject snow = json.getJSONObject("snow");
        	if(snow.has("3h")){
        		precipitation.setRain(snow.getDouble("3h"));
        	}
        	else{
        		
        		precipitation.setSnow(0);
        	}
        }
        else{
        	
        	precipitation.setSnow(0);
        }
        
        forecast.setPrecipitation(precipitation);
        
        return forecast;
	}
	
	private double convertKelvinToCelsius(double kelvin){
		
		return kelvin - 273.15;
	}
	
	private double convertMphToKph(double mph){
		
		return mph * 1.6;
	}

	//GETTERS y SETTERS
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlForecast() {
		return urlForecast;
	}

	public void setUrlForecast(String urlForecast) {
		this.urlForecast = urlForecast;
	}

	public RestProvider getRestProvider() {
		return restProvider;
	}

	public void setRestProvider(RestProvider restProvider) {
		this.restProvider = restProvider;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
}
