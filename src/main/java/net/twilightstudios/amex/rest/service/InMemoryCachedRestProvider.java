package net.twilightstudios.amex.rest.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InMemoryCachedRestProvider implements RestProvider {

	private long cacheTTL;	
	private Map<String,CacheContent> cache;
	
	public InMemoryCachedRestProvider(){
		
		cache = new HashMap<String, CacheContent>();
		cacheTTL = 300000;
	}
	
	@Override
	public String retrieveRawInformation(String urlString) throws IOException{
		
		CacheContent content = cache.get(urlString);
		if(content != null){
			
			if(System.currentTimeMillis() - content.getTimestamp().getTime() <= cacheTTL){
				
				return content.getJson();
			}
			
		}
		
		//Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("zen.es.hphis.com", 8080));		
		URL urlObject = new URL(urlString);
		
		System.out.println("Retrieving: " + urlString);
		
		//HttpURLConnection connection = (HttpURLConnection)urlObject.openConnection(proxy);
		HttpURLConnection connection = (HttpURLConnection)urlObject.openConnection();
		connection.setRequestMethod("GET");
		connection.setDoOutput(false);
		connection.setDoInput(true);
		connection.connect();
		
		
		StringBuilder builder = new StringBuilder();
		
		try(BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
								
			String line;				
			while((line = in.readLine()) != null){
				
				builder.append(line);
			}
		
		}
		
		String result = builder.toString();
		
		content = new CacheContent(new Date(),result); 
		cache.put(urlString, content);
		
		return result;
	}

	public long getCacheTTL() {
		return cacheTTL;
	}

	public void setCacheTTL(long cacheTTL) {
		this.cacheTTL = cacheTTL;
	}
	
	private class CacheContent{
		
		private Date timestamp;
		private String json;
		
		public CacheContent(Date timestamp, String json) {
			super();
			this.timestamp = timestamp;
			this.json = json;
		}

		public Date getTimestamp() {
			return timestamp;
		}
		
		public String getJson() {
			return json;
		}
	}


}
