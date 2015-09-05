package net.twilightstudios.amex.rest.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SimpleRestProvider implements RestProvider{

	private static final Log log = LogFactory.getLog(SimpleRestProvider.class); 
	
	public String retrieveRawInformation(String urlString) throws IOException{
		

		//Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("zen.es.hphis.com", 8080));		
		URL urlObject = new URL(urlString);
		
		log.info("Retrieving: " + urlString);
		
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
		
		//content = new CacheContent(new Date(),result); 
		//cache.put(urlString, content);
		
		return result;		
	}

}