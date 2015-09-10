package net.twilightstudios.amex.places.service.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import net.twilightstudios.amex.places.entity.Coordinates;
import net.twilightstudios.amex.places.entity.Place;
import net.twilightstudios.amex.places.service.PlacesService;
import net.twilightstudios.amex.places.service.google.GooglePlacesServiceProvider;

import org.json.JSONException;

public class PlacesServiceImpl implements PlacesService {

	private GooglePlacesServiceProvider googlePlacesService;
	
	
	
	public GooglePlacesServiceProvider getGooglePlacesService() {
		return googlePlacesService;
	}

	public void setGooglePlacesService(
			GooglePlacesServiceProvider googlePlacesService) {
		this.googlePlacesService = googlePlacesService;
	}

	@Override
	public byte[] retrieveImage(String photoId) throws IOException {

		return googlePlacesService.retrieveImage(photoId);
	}
	
	@Override
	public List<Place> retrieveRestaurants(Coordinates coordinates) throws JSONException, IOException {
		return googlePlacesService.retrievePlaces(coordinates, Arrays.asList("restaurant","cafe"));
	}
	
	@Override
	public List<Place> retrieveActivities(Coordinates coordinates) throws JSONException, IOException {
		return googlePlacesService.retrievePlaces(coordinates, Arrays.asList("aquarium","museum","art_gallery","stadium","church","mosque"));
	}
}
