package net.twilightstudios.amex.places.service.google;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.twilightstudios.amex.places.entity.Coordinates;
import net.twilightstudios.amex.places.entity.OpeningDays;
import net.twilightstudios.amex.places.entity.Place;
import net.twilightstudios.amex.places.service.PlacesServiceProvider;
import net.twilightstudios.amex.rest.service.ApiKeyProvider;
import net.twilightstudios.amex.rest.service.RestProvider;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GooglePlacesServiceProvider implements PlacesServiceProvider {
	
	private static final Log log = LogFactory.getLog(GooglePlacesServiceProvider.class);
	private final String apiKey;
	
	private RestProvider restProvider;
	
	private String providerId;
	
	private String url;
	private String urlDetail;
	private String urlPhoto;
	
	private String language;		
	private Integer radius;
	private Integer maxHeight;
	private Integer maxWidth;
	
	public GooglePlacesServiceProvider(ApiKeyProvider apiKeyProvider){
		
		this.apiKey = apiKeyProvider.getApiKey();
	}
	
	@Override
	public List<Place> retrievePlaces(Coordinates coord, List<String> type) throws JSONException, IOException {
				
		JSONObject json = retrieveRawInformation(coord,type,radius);		
		JSONArray list = json.getJSONArray("results");
		
		List<Place> resultado = new ArrayList<Place>(list.length());
		for(int i=0;i<list.length();i++){
			
			JSONObject obj = list.getJSONObject(i);
			Place place = buildPlace(obj);
			JSONObject detail = retrieveRawDetailInformation(place.getId());
			completePlace(place, detail);
			
			resultado.add(place);
		}
		
		return resultado;
	}
	
	private Place buildPlace(JSONObject obj) throws JSONException{
		
		Place place = new Place();
		
		place.setProvider(providerId);
		place.setId(obj.getString("place_id"));
		place.setAddressString(obj.getString("vicinity"));
		
		if(obj.has("price_level")){
			place.setPriceLevel(obj.getInt("price_level"));
		}
		else{
			
			place.setPriceLevel(-1);
		}
		
		if(obj.has("rating")){
			
			place.setRating(obj.getInt("rating"));
		}
		else{
			
			place.setRating(-1);
		}
		
		if(obj.has("photos")){
			
			JSONArray list = obj.getJSONArray("photos");
			if(list.length() >0){
				
				place.setPhotoId(list.getJSONObject(0).getString("photo_reference"));
			}
		}
		
		place.setName(obj.getString("name"));
		
		JSONObject location = obj.getJSONObject("geometry").getJSONObject("location");
		Coordinates coordinates = new Coordinates(location.getDouble("lat"), location.getDouble("lng"));
		place.setCoord(coordinates);
		
		return place;
	}
	
	private Place completePlace(Place place, JSONObject obj) throws JSONException{
		
		JSONObject result = obj.getJSONObject("result");
		
		if(result.has("international_phone_number")){
			
			place.setPhone(result.getString("international_phone_number"));
		}
		else if(result.has("formatted_phone_number")){
			
			place.setPhone(result.getString("formatted_phone_number"));			
		}
		
		if(result.has("formatted_address")){
			
			place.setAddressString(result.getString("formatted_address"));
		}
		
		if(result.has("opening_hours")){
			
			OpeningDays days = new OpeningDays();
			JSONArray weekdayText = result.getJSONObject("opening_hours").getJSONArray("weekday_text");

			String[] openingHours = new String[7];			
			for(int i=0;i<7;i++){
				
				openingHours[i] = weekdayText.get(i).toString();
			}
			
			days.setOpeningHours(openingHours);
			
			place.setOpenningDays(days);
		}
		
		return place;
	}
	
	protected JSONObject retrieveRawInformation(Coordinates coord, List<String> type, int radius) throws IOException, JSONException{
		
		StringBuilder urlBuilder = new StringBuilder(url);
		urlBuilder.append("?location=");
		urlBuilder.append(coord.getLat());
		urlBuilder.append(",");
		urlBuilder.append(coord.getLon());
		urlBuilder.append("&radius=");
		urlBuilder.append(radius);
		urlBuilder.append("&language=");
		urlBuilder.append(language);
		urlBuilder.append("&rankby=prominence");
		urlBuilder.append("&types=");
		urlBuilder.append(StringUtils.join(type, '|'));
		urlBuilder.append("&key=");
		urlBuilder.append(apiKey);
		
		String urlString = urlBuilder.toString();
		
		String genreJson = restProvider.retrieveRawInformation(urlString);
		
		JSONObject json = new JSONObject(genreJson);
		
		return json;
	}
	
	protected JSONObject retrieveRawDetailInformation(String placeId) throws IOException, JSONException{
		
		StringBuilder urlBuilder = new StringBuilder(urlDetail);
		urlBuilder.append("?placeid=");
		urlBuilder.append(placeId);
		urlBuilder.append("&language=");
		urlBuilder.append(language);
		urlBuilder.append("&key=");
		urlBuilder.append(apiKey);
		
		String urlString = urlBuilder.toString();
		
		String genreJson = restProvider.retrieveRawInformation(urlString);
		
		JSONObject json = new JSONObject(genreJson);
		
		return json;
	}

	
	@Override
	public byte[] retrieveImage(String photoId) throws IOException{
		
		StringBuilder urlBuilder = new StringBuilder(urlPhoto);
		urlBuilder.append("?photoreference=");
		urlBuilder.append(photoId);
		
		if(maxHeight != null){
			urlBuilder.append("&maxheight=");
			urlBuilder.append(maxHeight);
		}
		else if(maxWidth != null){
			
			urlBuilder.append("&maxwidth=");
			urlBuilder.append(maxWidth);
		}
		else{
			
			throw new IllegalArgumentException("Al menos tiene que estar definido uno de los atributos maxHeight o maxWidth");
		}
		
		urlBuilder.append("&key=");
		urlBuilder.append(apiKey);
		
		String urlString = urlBuilder.toString();
		
		return restProvider.retrieveRawImage(urlString);		
	}

	// GETTERS y SETTERS
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlPhoto() {
		return urlPhoto;
	}

	public void setUrlPhoto(String urlPhoto) {
		this.urlPhoto = urlPhoto;
	}
	
	public String getUrlDetail() {
		return urlDetail;
	}

	public void setUrlDetail(String urlDetail) {
		this.urlDetail = urlDetail;
	}

	public Integer getRadius() {
		return radius;
	}

	public void setRadius(Integer radius) {
		this.radius = radius;
	}

	public Integer getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(Integer maxHeight) {
		this.maxHeight = maxHeight;
	}

	public Integer getMaxWidth() {
		return maxWidth;
	}

	public void setMaxWidth(Integer maxWidth) {
		this.maxWidth = maxWidth;
	}

	public RestProvider getRestProvider() {
		return restProvider;
	}

	public void setRestProvider(RestProvider restProvider) {
		this.restProvider = restProvider;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String getProviderId() {
		return providerId;
	}
	
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
}
