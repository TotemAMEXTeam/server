package net.twilightstudios.amex.language.service.impl;

import java.io.IOException;

import net.twilightstudios.amex.geo.entity.Country;
import net.twilightstudios.amex.geo.service.GeolocationService;
import net.twilightstudios.amex.language.dao.LanguageDAO;
import net.twilightstudios.amex.language.entity.Language;
import net.twilightstudios.amex.language.service.LanguageService;

import org.json.JSONException;

public class BasicLanguageServiceImpl implements LanguageService{

	private GeolocationService geolocationService;
	private LanguageDAO languageDAO;
	
	@Override
	public Language getLanguage(String city) throws JSONException, IOException {
		
		Country country = geolocationService.getCountry(city);
		Language language = languageDAO.getLanguageByStandardCode(country.getLanguage());
		
		return language;		
	}


	//Getters y Setters
	
	public GeolocationService getGeolocationService() {
		return geolocationService;
	}
	
	public void setGeolocationService(GeolocationService geolocationService) {
		this.geolocationService = geolocationService;
	}

	public LanguageDAO getLanguageDAO() {
		return languageDAO;
	}

	public void setLanguageDAO(LanguageDAO languageDAO) {
		this.languageDAO = languageDAO;
	}
}
