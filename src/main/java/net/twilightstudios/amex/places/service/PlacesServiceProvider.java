package net.twilightstudios.amex.places.service;

import java.io.IOException;
import java.util.List;

import net.twilightstudios.amex.places.entity.Coordinates;
import net.twilightstudios.amex.places.entity.Place;

import org.json.JSONException;

public interface PlacesServiceProvider {

	public List<Place> retrievePlaces(Coordinates coord, List<String> type) throws JSONException, IOException;
	public byte[] retrieveImage(String photoId) throws JSONException, IOException;
}
