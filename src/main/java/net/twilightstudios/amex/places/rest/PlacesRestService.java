package net.twilightstudios.amex.places.rest;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.WebApplicationException;

import net.twilightstudios.amex.places.entity.Coordinates;
import net.twilightstudios.amex.places.entity.Place;
import net.twilightstudios.amex.places.service.PlacesService;

import org.json.JSONException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/places")
public class PlacesRestService {

	private PlacesService placesService;

	public PlacesService getPlacesService() {
		return placesService;
	}

	public void setPlacesService(PlacesService placesService) {
		this.placesService = placesService;
	}
	
	@RequestMapping(value="/restaurants", method=RequestMethod.GET, produces={"application/json;charset:UTF-8"})
	public List<Place> retrieveRestaurants(Coordinates coord) throws IOException, JSONException {
		try {
			return placesService.retrieveRestaurants(coord);
		} catch (Exception e) {
			 throw new WebApplicationException(e);
		}
	}
	
	@RequestMapping(value="/activities", method=RequestMethod.GET, produces={"application/json;charset:UTF-8"})
	public List<Place> retrieveActivities(Coordinates coord) throws IOException, JSONException {
		try {
			return placesService.retrieveActivities(coord);
		} catch (Exception e) {
			 throw new WebApplicationException(e);
		}
	}
	
	
	@RequestMapping(value="/image/{photoId}", method=RequestMethod.GET, produces={"image/jpeg"})
	public byte[] retrieveImage(@PathVariable String photoId) throws IOException, JSONException {
		try {
			return placesService.retrieveImage(photoId);
		} catch (Exception e) {
			 throw new WebApplicationException(e);
		}
	}
}
