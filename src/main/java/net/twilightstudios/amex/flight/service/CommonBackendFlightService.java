package net.twilightstudios.amex.flight.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import net.twilightstudios.amex.flight.entity.Flight;

public interface CommonBackendFlightService {
	
	
	/**
	 * Returns list of flights scheduled for today from specified airport
	 * @param airport Airport name
	 * @return list of flights scheduled for today. Empty list if there are no flights.
	 */
	public List<Flight> retrieveDailyFlights (String airport) throws IOException, ParseException;

}
