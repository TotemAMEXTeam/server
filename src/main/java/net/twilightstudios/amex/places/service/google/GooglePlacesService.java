package net.twilightstudios.amex.places.service.google;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.twilightstudios.amex.places.entity.Coordinates;
import net.twilightstudios.amex.places.entity.Place;
import net.twilightstudios.amex.places.service.PlacesService;
import net.twilightstudios.amex.rest.service.AbstractRestService;
import net.twilightstudios.amex.rest.service.ApiKeyProvider;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GooglePlacesService extends AbstractRestService implements PlacesService {

	private static final Log log = LogFactory.getLog(GooglePlacesService.class);
	private final String apiKey;
	
	
	private String url;
	private String urlPhoto;
	private Integer radius;
	private Integer maxHeight;
	private Integer maxWidth;
	
	public GooglePlacesService(ApiKeyProvider apiKeyProvider){
		
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
			resultado.add(place);
		}
		
		return resultado;
	}
	
	private Place buildPlace(JSONObject obj) throws JSONException{
		
		Place place = new Place();
		place.setId(obj.getString("place_id"));
		place.setAddressString(obj.getString("vicinity"));
		
		if(obj.has("price_level")){
			place.setPriceLevel(obj.getInt("price_level"));
		}
		else{
			
			place.setPriceLevel(-1);
		}
		
		if(obj.has("photos")){
			
			JSONArray list = obj.getJSONArray("photos");
			if(list.length() >0){
				
				place.setPhotoId(list.getJSONObject(0).getString("photo_reference"));
			}
		}
		
		place.setName(obj.getString("name"));
		
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
		urlBuilder.append("&rankby=prominence");
		urlBuilder.append("&types=");
		urlBuilder.append(StringUtils.join(type, '|'));
		urlBuilder.append("&key=");
		urlBuilder.append(apiKey);
		
		String urlString = urlBuilder.toString();
		
		String genreJson = retrieveRawInformation(urlString);
		
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
		//Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("zen.es.hphis.com", 8080));		
		URL urlObject = new URL(urlString);
		
		log.info("Retrieving: " + urlString);
		
		//HttpURLConnection connection = (HttpURLConnection)urlObject.openConnection(proxy);
		HttpURLConnection connection = (HttpURLConnection)urlObject.openConnection();
		connection.setRequestMethod("GET");
		connection.setDoOutput(false);
		connection.setDoInput(true);
		connection.connect();
		
		byte[] resultado;
		
		try(InputStream in = connection.getInputStream()){
								
			resultado = IOUtils.toByteArray(in);		
		}
		
		return resultado;		
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
}
