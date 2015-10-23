package net.twilightstudios.amex.flight.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import net.twilightstudios.amex.flight.entity.Flight;
import net.twilightstudios.amex.flight.entity.FlightStatus;
import net.twilightstudios.amex.flight.service.FlightService;
import net.twilightstudios.amex.flight.service.OffLineFlightService;
import net.twilightstudios.amex.flight.service.OnLineFlightService;

public class FlightServiceImpl implements FlightService {
	
	private OnLineFlightService onLineFlightService;
	private OffLineFlightService offLineFlightService;
	private String airport;

	public String getAirport() {
		return airport;
	}

	public void setAirport(String airport) {
		this.airport = airport;
	}

	public OnLineFlightService getOnLineFlightService() {
		return onLineFlightService;
	}

	public void setOnLineFlightService(OnLineFlightService onLineFlightService) {
		this.onLineFlightService = onLineFlightService;
	}

	public OffLineFlightService getOffLineFlightService() {
		return offLineFlightService;
	}

	public void setOffLineFlightService(OffLineFlightService offLineFlightService) {
		this.offLineFlightService = offLineFlightService;
	}

	@Override
	public FlightStatus retrieveFlightStatus(String id, String date)
			throws IOException, ParseException, IllegalStateException {
		return onLineFlightService.retrieveFlightStatus(id, date);
	}

	@Override
	public List<Flight> retrieveDailyFlights(String airport)
			throws IOException, ParseException {
		return offLineFlightService.retrieveDailyFlights(airport);
	}

	@Override
	public List<Flight> retrieveFlights(String flightId) throws IOException {
		return offLineFlightService.retrieveFlights(flightId);
	}
	
	public void updateFlights() throws IOException {
		try {
			List<Flight> onLineFlights = onLineFlightService.retrieveDailyFlights(airport);
			List<Flight> offLineFlights = offLineFlightService.retrieveDailyFlights(airport);
			offLineFlightService.updateFlights(onLineFlights, offLineFlights);
		} catch (ParseException e) {
			throw new IOException (e.getMessage(), e);
		}
	}

}
