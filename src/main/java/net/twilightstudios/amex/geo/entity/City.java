package net.twilightstudios.amex.geo.entity;

public class City {

	private Long id;
	private String name;
	private Long country;
	private Long language;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getCountry() {
		return country;
	}
	public void setCountry(Long country) {
		this.country = country;
	}
	public Long getLanguage() {
		return language;
	}
	public void setLanguage(Long language) {
		this.language = language;
	}
}
