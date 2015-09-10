package net.twilightstudios.amex.places.service;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import net.twilightstudios.amex.places.entity.Coordinates;
import net.twilightstudios.amex.places.entity.Place;

public interface PlacesService {

	public List<Place> retrieveRestaurants(Coordinates coordinates) throws JSONException, IOException;
	public List<Place> retrieveActivities(Coordinates coordinates) throws JSONException, IOException;
	public byte[] retrieveImage(String photoId) throws IOException;
}
