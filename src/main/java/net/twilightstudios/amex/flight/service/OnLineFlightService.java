package net.twilightstudios.amex.flight.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import net.twilightstudios.amex.flight.entity.Flight;
import net.twilightstudios.amex.flight.entity.FlightStatus;

public interface OnLineFlightService extends CommonBackendFlightService{
	
	/**
	 * Returns a flight status. 
	 * @param id Flight id
	 * @param date Departure date, format yyyy-MM-dd+HH:mm
	 * @return Current flight status. If flight covers whole route between AENA airports, result will have arrival information.
	 * 		   If not, it will only have departure information. Returns null if flight is not found.
	 * @throws IOException
	 * @throws ParseException
	 * @throws IllegalStateException
	 */
	public FlightStatus retrieveFlightStatus(String id, String date) throws IOException, ParseException, IllegalStateException;


}
