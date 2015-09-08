package net.twilightstudios.amex.places.entity;


public class Place {

	private String provider;
	
	private Coordinates coord;
	private String id;
	
	private String addressString;
	private String phone;
	private String name;
	
	private OpeningDays openningDays;
	
	private double rating;
	private int priceLevel;
	
	private String photoId;
	
	public Coordinates getCoord() {
		return coord;
	}
	public void setCoord(Coordinates coord) {
		this.coord = coord;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAddressString() {
		return addressString;
	}
	public void setAddressString(String addressString) {
		this.addressString = addressString;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPriceLevel() {
		return priceLevel;
	}
	public void setPriceLevel(int priceLevel) {
		this.priceLevel = priceLevel;
	}
	public String getPhotoId() {
		return photoId;
	}
	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public OpeningDays getOpenningDays() {
		return openningDays;
	}
	
	public void setOpenningDays(OpeningDays openningDays) {
		this.openningDays = openningDays;
	}
}
