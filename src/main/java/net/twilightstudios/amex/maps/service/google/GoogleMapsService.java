package net.twilightstudios.amex.maps.service.google;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import net.twilightstudios.amex.maps.service.MapsService;
import net.twilightstudios.amex.places.entity.Coordinates;
import net.twilightstudios.amex.rest.service.ApiKeyProvider;
import net.twilightstudios.amex.rest.service.RestProvider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GoogleMapsService implements MapsService {
	
	private static final Log log = LogFactory.getLog(GoogleMapsService.class); 
	
	private final String apiKey;
		
	private RestProvider restProvider;
	
	private String url;		
	private String size;
	private String maptype;
	private Integer zoom;
	
	public GoogleMapsService(ApiKeyProvider apiKeyProvider) {

		this.apiKey = apiKeyProvider.getApiKey();
	}

	
	@Override
	public byte[] retrieveMap(Coordinates coordinates) throws MalformedURLException, IOException {

		StringBuilder urlBuilder = new StringBuilder(url);
		urlBuilder.append("?center=");
		urlBuilder.append(coordinates.getLat());
		urlBuilder.append(",");
		urlBuilder.append(coordinates.getLon());
		urlBuilder.append("&size=");
		urlBuilder.append(size);
		urlBuilder.append("&maptype=");
		urlBuilder.append(maptype);
		urlBuilder.append("&zoom=");
		urlBuilder.append(zoom);
		urlBuilder.append("&key=");
		urlBuilder.append(apiKey);
		
		String urlString = urlBuilder.toString();
		
		return restProvider.retrieveRawImage(urlString);
	}
	

	public byte[] retrieveMap(List<Coordinates> coordinates)
			throws MalformedURLException, IOException {
	
		StringBuilder urlBuilder = new StringBuilder(url);
		urlBuilder.append("?markers=");
		urlBuilder.append(serializeCoordinates(coordinates));
		urlBuilder.append("&size=");
		urlBuilder.append(size);
		urlBuilder.append("&maptype=");
		urlBuilder.append(maptype);
		urlBuilder.append("&key=");
		urlBuilder.append(apiKey);
		
		String urlString = urlBuilder.toString();
		
		return restProvider.retrieveRawImage(urlString);
	}
		
	private String serializeCoordinates(List<Coordinates> coordinatesList){
		
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		for(Coordinates coordinates:coordinatesList){
			
			if(!first){
				
				builder.append("|");
			}
			else{
				
				first = false;
			}
			
			builder.append(coordinates.getLat()).append(",").append(coordinates.getLon());
		}
		
		return builder.toString();
	}
	
	//-- Getters y setters
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getMaptype() {
		return maptype;
	}

	public void setMaptype(String maptype) {
		this.maptype = maptype;
	}

	public Integer getZoom() {
		return zoom;
	}

	public void setZoom(Integer zoom) {
		this.zoom = zoom;
	}

	public RestProvider getRestProvider() {
		return restProvider;
	}

	public void setRestProvider(RestProvider restProvider) {
		this.restProvider = restProvider;
	}	
}
