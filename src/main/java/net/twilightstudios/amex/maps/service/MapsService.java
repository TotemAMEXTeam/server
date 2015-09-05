package net.twilightstudios.amex.maps.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import net.twilightstudios.amex.places.entity.Coordinates;

public interface MapsService {

	public byte[] retrieveMap(Coordinates coordinates) throws MalformedURLException, IOException;
	public byte[] retrieveMap(List<Coordinates> coordinates) throws MalformedURLException, IOException;
}
