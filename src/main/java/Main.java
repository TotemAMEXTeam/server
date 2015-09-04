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
		
		BeanFactory factory = new XmlBeanFactory(new FileSystemResource("placesContext.xml"));
			
		GeolocationService service = (GeolocationService)factory.getBean("geolocationService");		
		MapsService mapsService = (MapsService)factory.getBean("mapsService"); 
		PlacesService placesService = (PlacesService)factory.getBean("placesService");
		
		try{
			
			Coordinates coordinates = service.geolocateCity("Barcelona", "ES");
			
			printRestaurants(coordinates,placesService);
			
			byte[] map = mapsService.retrieveMap(coordinates);
			FileUtils.writeByteArrayToFile(new File("c:\\pruebaMap.png"), map);
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

}
