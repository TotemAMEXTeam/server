package net.twilightstudios.amex.flight.entity;

import java.util.Date;

public class Flight {
	
	private String flightNumber;
	private String origin;
	private String destiny;
	private String company;
	private Date scheduledDeparture;
	
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestiny() {
		return destiny;
	}
	public void setDestiny(String destiny) {
		this.destiny = destiny;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Date getScheduledDeparture() {
		return scheduledDeparture;
	}
	public void setScheduledDeparture(Date scheduledDeparture) {
		this.scheduledDeparture = scheduledDeparture;
	}
	
	

}
