package net.twilightstudios.amex.util.rest.cache.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.twilightstudios.amex.util.rest.RestProvider;
import net.twilightstudios.amex.util.rest.cache.entity.CacheContent;

public class InMemoryCachedRestProvider implements RestProvider {

	private long cacheTTL;	
	private Map<String,CacheContent> cache;
	
	private RestProvider delegate;
	
	public InMemoryCachedRestProvider(RestProvider delegate){
		
		cache = new HashMap<String, CacheContent>();
		cacheTTL = 3600000;
		
		this.delegate = delegate;
	}
	
	@Override
	public String retrieveRawInformation(String urlString) throws IOException{
		
		CacheContent content = cache.get(urlString);
		if(content != null){
			
			if(System.currentTimeMillis() - content.getTimestamp().getTime() <= cacheTTL){
				
				return content.getJson();
			}
			
		}
				
		String result = delegate.retrieveRawInformation(urlString);
		
		content = new CacheContent(urlString, new Date(),result); 
		cache.put(urlString, content);
		
		return result;
	}
	
	@Override
	public byte[] retrieveRawImage(String urlString) throws IOException {
	
		return delegate.retrieveRawImage(urlString);
	}

	public long getCacheTTL() {
		return cacheTTL;
	}

	public void setCacheTTL(long cacheTTL) {
		this.cacheTTL = cacheTTL;
	}

}
