package net.twilightstudios.amex.flight.entity;

import java.util.Date;

public class FlightDate {

	private Date departure;
	private Date arrival;
	
	public Date getDeparture() {
		return departure;
	}
	
	public void setDeparture(Date departure) {
		this.departure = departure;
	}
	
	public Date getArrival() {
		return arrival;
	}
	
	public void setArrival(Date arrival) {
		this.arrival = arrival;
	}
}
