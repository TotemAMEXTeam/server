package net.twilightstudios.amex.rest.service;

public class SimpleApiKeyProvider implements ApiKeyProvider{

	private String apiKey;

	public SimpleApiKeyProvider(String apiKey) {
	
		this.apiKey = apiKey;
	}

	@Override
	public String getApiKey() {
	
		return apiKey;
	}	
}
