package net.twilightstudios.amex.geo.entity;

public class Country {

	private Long id;
	private String name;
	private String standardCode;
	
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
	public String getStandardCode() {
		return standardCode;
	}
	public void setStandardCode(String standardCode) {
		this.standardCode = standardCode;
	}	
}
