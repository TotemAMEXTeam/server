package net.twilightstudios.amex.places.entity;

import java.util.HashMap;
import java.util.Map;

public class OpeningDays {

	public enum Day {

		MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
	}
	
	private Map<Day,String> openingHours;
	
	public OpeningDays() {
	
		openingHours = new HashMap<OpeningDays.Day, String>();
	}
	
	public void setOpeningHours(Day day, String hours){
		
		openingHours.put(day, hours);
	}
	
	public String getOpeningHours(Day day){
		
		return openingHours.get(day);
	}
}
