package net.twilightstudios.amex.rest.service;

import java.io.IOException;

public interface RestProvider {

	public String retrieveRawInformation(String urlString) throws IOException;
	
}
