package net.twilightstudios.amex.maps.rest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.WebApplicationException;

import net.twilightstudios.amex.maps.service.MapsService;
import net.twilightstudios.amex.places.entity.Coordinates;

import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/maps")
public class MapsRestService {
	
	private MapsService mapsService;
	
	public MapsService getMapsService() {
		return mapsService;
	}

	public void setMapsService(MapsService mapsService) {
		this.mapsService = mapsService;
	}

	@RequestMapping(value="/retrieve", method=RequestMethod.GET, produces={"image/png"})
	public byte[] retrieveMap(Coordinates coordinates) throws IOException, JSONException {
		try {
			return mapsService.retrieveMap(coordinates);			
		} catch (Exception e) {
			 throw new WebApplicationException(e);
		}
	}
	
	@RequestMapping(value="/retrieveByList", method=RequestMethod.GET, produces={"image/png"})
	public byte[] retrieveMap(Double[] lat,Double[] lon) throws IOException, JSONException {
		try {
			
			if(lat.length != lon.length){
				
				throw new IllegalArgumentException("La cantidad de longitudes y latitudes no coincide");
			}

			List<Coordinates> coordinatesList = new ArrayList<>(lat.length);
			
			for(int i=0;i<lat.length;i++){
			
				Coordinates coordinates = new Coordinates(lat[i], lon[i]);
				coordinatesList.add(coordinates);
			}
			
			return mapsService.retrieveMap(coordinatesList);
			
		} catch (Exception e) {
			 throw new WebApplicationException(e);
		}
	}
}