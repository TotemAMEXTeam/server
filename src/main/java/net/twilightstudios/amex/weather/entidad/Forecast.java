package net.twilightstudios.amex.weather.entidad;

import java.util.Date;

public class Forecast {

	private Date timestamp;
	private String city;
	
	private double humidity;
	private double pressure;
	private int cloudiness;
	
	private Temperature temperature;
	private Wind wind;	
	private Precipitation precipitation;	
	
	private Summary summary;
	
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Temperature getTemperature() {
		return temperature;
	}
	public void setTemperature(Temperature temperature) {
		this.temperature = temperature;
	}
	public Wind getWind() {
		return wind;
	}
	public void setWind(Wind wind) {
		this.wind = wind;
	}
	public double getHumidity() {
		return humidity;
	}
	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}
	public double getPressure() {
		return pressure;
	}
	public void setPressure(double pressure) {
		this.pressure = pressure;
	}
	public int getCloudiness() {
		return cloudiness;
	}
	public void setCloudiness(int cloudiness) {
		this.cloudiness = cloudiness;
	}
	public Summary getSummary() {
		return summary;
	}
	public void setSummary(Summary summary) {
		this.summary = summary;
	}
	public Precipitation getPrecipitation() {
		return precipitation;
	}
	public void setPrecipitation(Precipitation precipitation) {
		this.precipitation = precipitation;
	}
}
