package net.twilightstudios.amex.util.timer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import net.twilightstudios.amex.flight.dao.FlightDAO;
import net.twilightstudios.amex.flight.entity.Flight;
import net.twilightstudios.amex.flight.service.FlightService;
import net.twilightstudios.amex.util.persistence.TransactionManager;

public class FlightsTimer extends TimerTask {
	
	private FlightService flightService;
	private Long period;

	public Long getPeriod() {
		return period;
	}

	public void setPeriod(Long period) {
		this.period = period;
	}

	public FlightService getFlightService() {
		return flightService;
	}

	public void setFlightService(FlightService flightService) {
		this.flightService = flightService;
	}

	@Override
	public void run() {
		try {
			flightService.updateFlights();			
		}
		catch (Exception e) {
			throw new RuntimeException("Failed to update flights list", e);
		}
	}

}
