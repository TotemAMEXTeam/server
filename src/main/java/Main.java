import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.twilightstudios.amex.geo.service.GeolocationService;
import net.twilightstudios.amex.maps.service.MapsService;
import net.twilightstudios.amex.places.entity.Coordinates;
import net.twilightstudios.amex.places.entity.Place;
import net.twilightstudios.amex.places.service.PlacesService;
import net.twilightstudios.amex.weather.entidad.Forecast;
import net.twilightstudios.amex.weather.service.WeatherService;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

public class Main {

	//private static String apiKey = "AIzaSyA27x5cAGeDq578qYGJH-HB3mIak4Esi9Q";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		String city = "Barcelona";
		String country = "ES";
		
		BeanFactory factory = new XmlBeanFactory(new FileSystemResource("placesContext.xml"));
			
		GeolocationService service = (GeolocationService)factory.getBean("geolocationService");		
		MapsService mapsService = (MapsService)factory.getBean("mapsService"); 
		PlacesService placesService = (PlacesService)factory.getBean("placesService");
				
		
		try{
			
			Coordinates coordinates = service.geolocateCity(city, country);
			
			printRestaurants(coordinates,placesService);
			
			byte[] map = mapsService.retrieveMap(coordinates);
			FileUtils.writeByteArrayToFile(new File("c:\\pruebaMap.png"), map);
						
		}
		catch(Exception e){
			
			e.printStackTrace();
		}
		
		BeanFactory factoryWeather = new XmlBeanFactory(new FileSystemResource("weatherContext.xml"));
		WeatherService weatherService = (WeatherService)factoryWeather.getBean("weatherService");
		
		try{
			Forecast forecast = weatherService.retrieveForecast(city);
			
			printForecast(forecast);
			
			List<Forecast> forecastList = weatherService.retrieve5dayForecast(city);
			for(Forecast day:forecastList){
				
				printForecast(day);
			}
		}
		catch(Exception e){
			
			e.printStackTrace();
		}
	}
	
	private static void printRestaurants(Coordinates coordinates,PlacesService service) throws JSONException, IOException{
		
		List<String> restaurantsType = new ArrayList<String>(Arrays.asList("restaurant","cafe"));
		
		List<Place> places = service.retrievePlaces(coordinates,restaurantsType);			
		
		int count =0;
		for(Place place:places){
			
			System.out.println("Place: " + place.getId() + " name: " + place.getName() + " " + place.getAddressString() + " Price level: " + place.getPriceLevel());
			
			if(place.getPhotoId() != null){
				byte[] image = service.retrieveImage(place.getPhotoId());
				FileUtils.writeByteArrayToFile(new File("c:\\prueba" + count++ + ".jpeg"), image);
			}
		}
	}

	private static void printForecast(Forecast forecast){
		
		System.out.println("Ciudad: " + forecast.getCity() + " Fecha: " + forecast.getTimestamp());
		System.out.println("Presión: " + forecast.getPressure() + "mbar Humedad: " + forecast.getHumidity() + "% Nubosidad: " + forecast.getCloudiness() + "%");
		System.out.println("Temperatura: " + forecast.getTemperature().getActual() + "C Max: " + forecast.getTemperature().getMax() + "C Min: " + forecast.getTemperature().getMin() + "C");
		System.out.println("Viento: " + forecast.getWind().getSpeed() + "Kph Dirección: " + forecast.getWind().getHeadingString());
		System.out.println("Lluvia: " + forecast.getPrecipitation().getRain() + "mm Nieve: " + forecast.getPrecipitation().getSnow() + "mm");
		System.out.println("Resumen: " + forecast.getSummary().getDescription() + " Icon: http://openweathermap.org/img/w/" + forecast.getSummary().getIcon() + ".png");
	}
}
