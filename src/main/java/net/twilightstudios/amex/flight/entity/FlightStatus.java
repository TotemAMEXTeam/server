package net.twilightstudios.amex.flight.entity;


public class FlightStatus {

	private String flightNumber;
	private Status status;
	
	private String origin;
	private String destiny;
	
	private String originTerminal;
	private String destinyTerminal;
	
	private FlightDate scheduled;	
	private	FlightDate estimated;
	
	private String checkInCounter;
	private String boardingGate;

	
	public String getFlightNumber() {
		return flightNumber;
	}
	
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public FlightDate getScheduled() {
		return scheduled;
	}
	
	public void setScheduled(FlightDate scheduled) {
		this.scheduled = scheduled;
	}
	
	public FlightDate getEstimated() {
		return estimated;
	}
	
	public void setEstimated(FlightDate estimated) {
		this.estimated = estimated;
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
	
	
}
