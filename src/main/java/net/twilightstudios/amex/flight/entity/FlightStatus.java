package net.twilightstudios.amex.flight.entity;

import java.util.Date;


public class FlightStatus {

	private Flight flight;
	private Status status;
	
	private String originTerminal;
	private String destinyTerminal;
	
	private Date scheduledArrival;
	
	private Date estimatedDeparture;
	private Date estimatedArrival;
	
	private String checkInCounter;
	private String boardingGate;
	
	private String hall;
	private String belt;

	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}

	public String getCheckInCounter() {
		return checkInCounter;
	}

	public void setCheckInCounter(String checkInCounter) {
		this.checkInCounter = checkInCounter;
	}

	public String getBoardingGate() {
		return boardingGate;
	}

	public void setBoardingGate(String boardingGate) {
		this.boardingGate = boardingGate;
	}

	public String getOriginTerminal() {
		return originTerminal;
	}

	public void setOriginTerminal(String originTerminal) {
		this.originTerminal = originTerminal;
	}

	public String getDestinyTerminal() {
		return destinyTerminal;
	}

	public void setDestinyTerminal(String destinyTerminal) {
		this.destinyTerminal = destinyTerminal;
	}

	public String getHall() {
		return hall;
	}

	public void setHall(String hall) {
		this.hall = hall;
	}

	public String getBelt() {
		return belt;
	}

	public void setBelt(String belt) {
		this.belt = belt;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public Date getScheduledArrival() {
		return scheduledArrival;
	}

	public void setScheduledArrival(Date scheduledArrival) {
		this.scheduledArrival = scheduledArrival;
	}

	public Date getEstimatedDeparture() {
		return estimatedDeparture;
	}

	public void setEstimatedDeparture(Date estimatedDeparture) {
		this.estimatedDeparture = estimatedDeparture;
	}

	public Date getEstimatedArrival() {
		return estimatedArrival;
	}

	public void setEstimatedArrival(Date estimatedArrival) {
		this.estimatedArrival = estimatedArrival;
	}
	
	
	
}
