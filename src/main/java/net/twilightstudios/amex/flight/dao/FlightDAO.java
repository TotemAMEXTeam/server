package net.twilightstudios.amex.flight.dao;

import java.util.List;

import net.twilightstudios.amex.flight.entity.Flight;

public interface FlightDAO {
	
	public List<Flight> getFlights();
	
	public List<Flight> getFlights(String flightId);
	
	public void deleteFlight(Flight flight);
	
	public void insert (Flight flight);
	
	public void update (Flight flight);
	
	public void deleteAll();

}
