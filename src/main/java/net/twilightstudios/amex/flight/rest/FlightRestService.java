package net.twilightstudios.amex.flight.rest;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.WebApplicationException;

import net.twilightstudios.amex.flight.entity.Flight;
import net.twilightstudios.amex.flight.entity.FlightStatus;
import net.twilightstudios.amex.flight.service.FlightService;

@RestController
@RequestMapping("/flight")
public class FlightRestService {

	private FlightService flightService;

	public FlightService getFlightService() {
		return flightService;
	}

	public void setFlightService(FlightService flightService) {
		this.flightService = flightService;
	}
	
	@RequestMapping(value="/flightStatus/{id}", method=RequestMethod.GET, produces={"application/json"})
	public @ResponseBody FlightStatus retrieveFlightStatus(@PathVariable String id, @RequestParam(value = "hprevista") String date) throws IOException {
		try {
			return flightService.retrieveFlightStatus(id, date);
		} catch (Exception e) {
			 throw new WebApplicationException(e);
		}
	}
	
	@RequestMapping(value="/dailyFlights/{airport}", method=RequestMethod.GET, produces={"application/json"})
	public @ResponseBody List<Flight> retrieveDailyFlights (@PathVariable String airport) throws IOException {
		try {
			return flightService.retrieveDailyFlights(airport);
		} catch (Exception e) {
			 throw new WebApplicationException(e);
		}
	}
	
	@RequestMapping(value="/getFlight", method=RequestMethod.GET, produces={"application/json"})
	public @ResponseBody List<Flight> retrieveFlights (@RequestParam(value = "flightId") String flightId) throws IOException {
		try {
			return flightService.retrieveFlights(flightId);
		} catch (Exception e) {
			 throw new WebApplicationException(e);
		}
	}
	
}
