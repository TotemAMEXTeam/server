package net.twilightstudios.amex.flight.service.database;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.hql.internal.ast.tree.MapValueNode;

import net.twilightstudios.amex.flight.dao.FlightDAO;
import net.twilightstudios.amex.flight.entity.Flight;
import net.twilightstudios.amex.flight.entity.FlightStatus;
import net.twilightstudios.amex.flight.service.FlightService;
import net.twilightstudios.amex.flight.service.OffLineFlightService;
import net.twilightstudios.amex.util.persistence.TransactionManager;
import net.twilightstudios.amex.util.rest.cache.entity.CacheContent;

public class DatabaseCachedFlightService implements OffLineFlightService {
	
	private TransactionManager manager;
	private FlightDAO dao;
	
	public TransactionManager getManager() {
		return manager;
	}

	public void setManager(TransactionManager manager) {
		this.manager = manager;
	}
	
	public FlightDAO getDao() {
		return dao;
	}

	public void setDao(FlightDAO dao) {
		this.dao = dao;
	}

	@Override
	public List<Flight> retrieveDailyFlights(String airport)
			throws IOException, ParseException {
		
		manager.beginTransactionOnCurrentSession();
		try {	
			List<Flight> flightsFromDb = dao.getFlights();
			manager.rollbackOnCurrentSession();
			return flightsFromDb;
		}
		catch (Exception e) {
			manager.rollbackOnCurrentSession();
			throw new IOException(e);
		}
	}

	@Override
	public List<Flight> retrieveFlights(String flightId) throws IOException {
		manager.beginTransactionOnCurrentSession();
		try {	
			List<Flight> flightsFromDb = dao.getFlights(flightId);
			manager.rollbackOnCurrentSession();
			return flightsFromDb;
		}
		catch (Exception e) {
			manager.rollbackOnCurrentSession();
			throw new IOException(e);
		}
	}

	@Override
	public void updateFlights(List<Flight> onLineFlights, List<Flight> offLineFlights) throws IOException {
		Map<String, Flight> mapOnLineFlights = new HashMap<String, Flight>();
		for (Flight f: onLineFlights) {
			mapOnLineFlights.put(f.getFlightNumber(), f);	
		}
		Map<String, Flight> mapOffLineFlights = new HashMap<String, Flight>();
		for (Flight f: offLineFlights) {
			mapOffLineFlights.put(f.getFlightNumber(), f);	
		}
		manager.beginTransactionOnCurrentSession();
		try {
			// Save new flights and update existent
			for (Flight f: onLineFlights) {
				if (mapOffLineFlights.get(f.getFlightNumber())==null) {
					dao.insert(f);
				}
				else {
					if (!mapOffLineFlights.get(f.getFlightNumber()).equals(f)) {
						dao.update(f);
					}
				}
			}
			// Delete old flights
			for (Flight f: offLineFlights) {
				if (mapOnLineFlights.get(f.getFlightNumber())==null) {
					dao.deleteFlight(f);
				}
			}
			manager.commitOnCurrentSession();
		}
		catch (Exception e) {
			manager.rollbackOnCurrentSession();
			throw new IOException(e);
		}
	}

}
