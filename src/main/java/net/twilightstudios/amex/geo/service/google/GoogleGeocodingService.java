package net.twilightstudios.amex.geo.service.google;

import java.io.IOException;

import net.twilightstudios.amex.geo.service.GeolocationService;
import net.twilightstudios.amex.places.entity.Coordinates;
import net.twilightstudios.amex.rest.service.ApiKeyProvider;
import net.twilightstudios.amex.rest.service.RestProvider;
import net.twilightstudios.amex.rest.service.SimpleRestProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleGeocodingService implements GeolocationService {

	private String url;
	private final String apiKey;

	private RestProvider restProvider;
	
	public GoogleGeocodingService(ApiKeyProvider apiKeyProvider) {
		super();
		this.apiKey = apiKeyProvider.getApiKey();
	}
	
	@Override
	public Coordinates geolocateCity(String cityName, String country) throws IOException, JSONException {

		JSONObject json = retrieveRawGeocode(cityName, country);		
		JSONArray list = json.getJSONArray("results");
		
		JSONObject object = list.getJSONObject(0);
		JSONObject geometry = object.getJSONObject("geometry");
		JSONObject location = geometry.getJSONObject("location");
		
		Coordinates result = new Coordinates(location.getDouble("lat"), location.getDouble("lng"));
		
		return result;
	}

	protected JSONObject retrieveRawGeocode(String cityName, String country) throws IOException, JSONException{
		
		StringBuilder urlBuilder = new StringBuilder(url);
		urlBuilder.append("?address=");
		urlBuilder.append(cityName);
		
		if(country!=null){
			urlBuilder.append("&components=country:");
			urlBuilder.append(country);
		}
		
		urlBuilder.append("&key=");
		urlBuilder.append(apiKey);
		
		String urlString = urlBuilder.toString();
		String genreJson = restProvider.retrieveRawInformation(urlString);
		
		return new JSONObject(genreJson); 
	}
	
	//GETTERs y SETTERS
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public RestProvider getRestProvider() {
		return restProvider;
	}

	public void setRestProvider(RestProvider restProvider) {
		this.restProvider = restProvider;
	}
}