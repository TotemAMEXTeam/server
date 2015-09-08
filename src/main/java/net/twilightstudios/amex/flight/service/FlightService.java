package net.twilightstudios.amex.flight.service;

import java.io.IOException;
import java.text.ParseException;

import net.twilightstudios.amex.flight.entity.FlightStatus;

public interface FlightService {

	public FlightStatus retrieveFlightStatus(String id, String date) throws IOException, ParseException;
	
}
