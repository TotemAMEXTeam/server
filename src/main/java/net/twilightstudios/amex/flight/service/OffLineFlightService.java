package net.twilightstudios.amex.flight.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import net.twilightstudios.amex.flight.entity.Flight;

public interface OffLineFlightService extends CommonBackendFlightService {
	
	/**
	 * Returns list of flights matching flightId
	 * @param flighId Flight id
	 * @return list of flights matching flightId
	 */
	public List<Flight> retrieveFlights (String flightId) throws IOException;
	
	/**
	 * Updates database flights list
	 * @throws IOException
	 */
	public void updateFlights(List<Flight> onLineFlights, List<Flight> offLineFlights) throws IOException;

}
