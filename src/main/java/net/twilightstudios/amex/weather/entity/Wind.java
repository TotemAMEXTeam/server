package net.twilightstudios.amex.weather.entity;

public class Wind {

	private static final String NORTH = "N";
	private static final String NORTHEAST = "NE";
	private static final String EAST = "E";
	private static final String SOUTHEAST = "SE";
	private static final String SOUTH = "S";
	private static final String SOUTHWEST = "SW";
	private static final String WEST = "W";
	private static final String NORTHWEST = "NW";
	
	private double speed;
	private double heading;
	
	public double getSpeed() {
		return speed;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public double getHeading() {
		return heading;
	}
	
	public void setHeading(double heading) {
		this.heading = heading;
	}
	
	public String getHeadingString(){
		
		if(heading>337.5 || heading<=22.5){
			
			return NORTH;
		}
		else if(heading > 22.5 && heading <=67.5){
			
			return NORTHEAST;
		}
		else if(heading > 67.5 && heading <=112.5){
			
			return EAST;
		}
		else if(heading >112.5 && heading <= 157.5){
			
			return SOUTHEAST;
		}
		else if(heading >157.5 && heading <= 202.5){
			
			return SOUTH;
		}
		else if(heading >202.5 && heading <= 247.5){
			
			return SOUTHWEST;
		}
		else if(heading >247.5 && heading <= 292.5){
			
			return WEST;
		}
		else{
			
			return NORTHWEST;
		}
	}
}
