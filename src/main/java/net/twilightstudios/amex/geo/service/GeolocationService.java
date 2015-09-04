package net.twilightstudios.amex.geo.service;

import java.io.IOException;

import net.twilightstudios.amex.places.entity.Coordinates;

import org.json.JSONException;

public interface GeolocationService {

	public Coordinates geolocateCity(String cityName,String country) throws IOException, JSONException;

}
