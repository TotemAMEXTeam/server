package net.twilightstudios.amex.places.entity;

import java.util.HashMap;

public class OpeningDays {

	public enum Day {

		MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
	}
	
	private String[] openingHours;
	
	public OpeningDays() {}
	
	public void setOpeningHours(String[] openingHours){
		
		this.openingHours = openingHours;
	}
	
	public String[] getOpeningHours(){
		
		return openingHours;
	}
}
