package net.twilightstudios.amex.maps.service.google;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.twilightstudios.amex.maps.service.MapsService;
import net.twilightstudios.amex.places.entity.Coordinates;
import net.twilightstudios.amex.rest.service.SimpleRestProvider;
import net.twilightstudios.amex.rest.service.ApiKeyProvider;

public class GoogleMapsService implements MapsService {
	
	private static final Log log = LogFactory.getLog(GoogleMapsService.class); 
	
	private final String apiKey;
	//center=Berkeley,CA&zoom=14&size=400x400&key=API_KEY";
	
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

	@Override
	public byte[] retrieveMap(String city) throws MalformedURLException, IOException  {

		StringBuilder urlBuilder = new StringBuilder(url);
		urlBuilder.append("?center=");
		urlBuilder.append(city);
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

}
