package net.twilightstudios.amex.geo.service.google;

import java.io.IOException;
import java.net.URLEncoder;

import net.twilightstudios.amex.geo.dao.CountryDao;
import net.twilightstudios.amex.geo.entity.Country;
import net.twilightstudios.amex.geo.service.GeolocationService;
import net.twilightstudios.amex.language.entity.Language;
import net.twilightstudios.amex.places.entity.Coordinates;
import net.twilightstudios.amex.util.rest.ApiKeyProvider;
import net.twilightstudios.amex.util.rest.RestProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleGeocodingService implements GeolocationService {

	private String url;
	private final String apiKey;
	private CountryDao countryDao; 
	
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

	@Override
	public Country getCountry(String city) throws JSONException, IOException {
	
		JSONObject json = retrieveRawGeocode(city, null);
		JSONObject firstResult = json.getJSONArray("results").getJSONObject(0);
		JSONArray addressComponents = firstResult.getJSONArray("address_components");
		
		boolean encontrado = false;
		String country = null;
		int index = 0;
		while(!encontrado && index < addressComponents.length()){
			
			JSONObject obj = addressComponents.getJSONObject(index++);
			JSONArray types = obj.getJSONArray("types");
			int indexType = 0;
			while(!encontrado && indexType < types.length()){
				
				encontrado = types.getString(indexType++).equals("country");
			}
			
			if(encontrado){
				
				country = obj.getString("short_name");
			}
		}
		
		return countryDao.getCountryByStandardCode(country);
	}
	
	protected JSONObject retrieveRawGeocode(String cityName, String country) throws IOException, JSONException{
		
		StringBuilder urlBuilder = new StringBuilder(url);
		urlBuilder.append("?address=");
		urlBuilder.append(URLEncoder.encode(cityName, "UTF-8"));
		
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

	public CountryDao getCountryDao() {
		return countryDao;
	}

	public void setCountryDao(CountryDao dao) {
		this.countryDao = dao;
	}
}
