package net.twilightstudios.amex.places.service.tripadvisor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.twilightstudios.amex.places.entity.Coordinates;
import net.twilightstudios.amex.places.entity.Place;
import net.twilightstudios.amex.places.entity.Rating;
import net.twilightstudios.amex.places.service.PlacesService;
import net.twilightstudios.amex.places.service.google.GooglePlacesServiceProvider;
import net.twilightstudios.amex.util.rest.ApiKeyProvider;
import net.twilightstudios.amex.util.rest.RestProvider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TripAdvisorPlacesService implements PlacesService {

	private static final Log log = LogFactory.getLog(TripAdvisorPlacesService.class);
	private final String apiKey;
	
	private GooglePlacesServiceProvider googleProvider;
	private RestProvider restProvider;
	
	private String providerId;
	private String language;
	
	private String urlMapping;
	private String urlList;
	
	public TripAdvisorPlacesService(ApiKeyProvider apiKeyProvider){
		
		this.apiKey = apiKeyProvider.getApiKey();
	}

	@Override
	public List<Place> retrieveRestaurants(Coordinates coordinates)
			throws JSONException, IOException {

		String locationID = retrieveLocationID(coordinates);		
		return retrievePlaces(locationID, "restaurants");
	}

	@Override
	public List<Place> retrieveActivities(Coordinates coordinates)
			throws JSONException, IOException {

		String locationID = retrieveLocationID(coordinates);		
		return retrievePlaces(locationID, "attractions");
	}


	@Override
	public byte[] retrieveImage(String photoId) throws IOException {

		JSONObject json= retrieveRawPhotos(photoId);
		JSONArray array = json.getJSONArray("data");
		
		boolean isBlessed = false;
		int index =0;
		while(!isBlessed && index<array.length()){
			
			JSONObject image = array.getJSONObject(index);
			
			if(image.getBoolean("is_blessed")){
				isBlessed = true;
			}
			else{
			
				index++;
			}
		}
		
		if(!isBlessed){
			
			index = 0;
		}
		
		JSONObject image = array.getJSONObject(index);
		String url = image.getJSONObject("images").getJSONObject("large").getString("url");
		
		return restProvider.retrieveRawImage(url);
	}

	private void completePlace(Place place) throws IOException{
		
		if(place.getCoord() == null){
		
			return;
		}
		
		Place googlePlace = googleProvider.retrievePlace(place.getCoord(), place.getName());
		
		if(googlePlace == null){
		
			return;
		}
		
		place.setOpenningDays(googlePlace.getOpenningDays());
		place.setPhone(googlePlace.getPhone());
		
	}
	
	private Place buildPlace(JSONObject json){
		
		Place place = new Place();
		place.setAddressString(json.getJSONObject("address_obj").getString("address_string"));
		
		Coordinates coord = new Coordinates(json.getDouble("latitude"), json.getDouble("longitude"));
		
		if(coord.getLat() != 0 || coord.getLon() != 0){
		
			place.setCoord(coord);
		}
		
		
		place.setId(json.getString("location_id"));
		place.setName(json.getString("name"));
		
		Rating rating = new Rating();
		rating.setRating(json.getDouble("rating"));
		rating.setRevisions(json.getInt("num_reviews"));
		
		place.setRating(rating);
		
		if(json.has("price_level") && !json.isNull("price_level")){
		
			place.setPriceLevel(json.getString("price_level").length());
		}
		else{
			
			place.setPriceLevel(-1);
		}
		
		place.setPhotoId(place.getId());
		place.setProvider(providerId);
		
		return place;
	}

	private JSONObject retrieveRawPhotos(String locationID) throws IOException{
		
		StringBuilder urlBuilder = new StringBuilder(urlList);
		urlBuilder.append(locationID);
		urlBuilder.append("/photos");
		urlBuilder.append("?lang=");
		urlBuilder.append(language);
		urlBuilder.append("&key=");

		urlBuilder.append(apiKey);
		
		String urlString = urlBuilder.toString();
		
		String genreJson = restProvider.retrieveRawInformation(urlString);
		JSONObject json = new JSONObject(genreJson);
		
		return json;
	}
	
	private List<Place> retrievePlaces(String locationID,String type) throws IOException{
		
		StringBuilder urlBuilder = new StringBuilder(urlList);
		urlBuilder.append(locationID);
		urlBuilder.append("/");
		urlBuilder.append(type);
		urlBuilder.append("?lang=");
		urlBuilder.append(language);
		urlBuilder.append("&key=");
		urlBuilder.append(apiKey);
		
		String urlString = urlBuilder.toString();
		
		String genreJson = restProvider.retrieveRawInformation(urlString);
		JSONObject json = new JSONObject(genreJson);
		
		JSONArray data = json.getJSONArray("data");
		
		List<Place> result = new ArrayList<>(data.length());
		for(int i=0;i<data.length();i++){
			
			Place place = buildPlace(data.getJSONObject(i));
			completePlace(place);
			result.add(place);
		}
		
		return result;
	}

	
	private String retrieveLocationID(Coordinates coord) throws IOException{
		
		StringBuilder urlBuilder = new StringBuilder(urlMapping);
		urlBuilder.append(coord.getLat());
		urlBuilder.append(",");
		urlBuilder.append(coord.getLon());
		urlBuilder.append("?lang=");
		urlBuilder.append(language);
		urlBuilder.append("&key=");
		urlBuilder.append(apiKey);
		
		String urlString = urlBuilder.toString();
		
		String genreJson = restProvider.retrieveRawInformation(urlString);
		JSONObject json = new JSONObject(genreJson);
		
		JSONArray ancestors = json.getJSONArray("data").getJSONObject(0).getJSONArray("ancestors");
		String locationID = null;
		
		boolean city = false;
		String cityLocation = "";
		
		boolean island = false;
		String islandLocation = "";
		
		for(int i=0;i< ancestors.length() && !(city && island);i++){
			
			JSONObject ancestor = ancestors.getJSONObject(i);
			if(ancestor.getString("level").equals("City") || ancestor.getString("level").equals("Municipality") ){
				
				city = true;
				cityLocation = ancestor.getString("location_id");
			}
			else if(ancestor.getString("level").equals("Island")){
				
				island = true;
				islandLocation = ancestor.getString("location_id");
			}
		}
		
		
		if(island){
			
			locationID = islandLocation;
		}
		else if(city){
			
			locationID = cityLocation;
		}
		
		return locationID;
	}
	
	//Getters y setters
	
	public RestProvider getRestProvider() {
		return restProvider;
	}

	public void setRestProvider(RestProvider restProvider) {
		this.restProvider = restProvider;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public GooglePlacesServiceProvider getProvider() {
		return googleProvider;
	}

	public void setProvider(GooglePlacesServiceProvider provider) {
		this.googleProvider = provider;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getUrlMapping() {
		return urlMapping;
	}

	public void setUrlMapping(String urlMapping) {
		this.urlMapping = urlMapping;
	}

	public String getUrlList() {
		return urlList;
	}

	public void setUrlList(String urlList) {
		this.urlList = urlList;
	}	
}
