package net.twilightstudios.amex.language.service.impl;

import java.io.IOException;
import java.util.List;

import net.twilightstudios.amex.geo.entity.Country;
import net.twilightstudios.amex.geo.service.GeolocationService;
import net.twilightstudios.amex.language.dao.LanguageDAO;
import net.twilightstudios.amex.language.dao.LanguageExpressionDAO;
import net.twilightstudios.amex.language.entity.Language;
import net.twilightstudios.amex.language.entity.LanguageExpression;
import net.twilightstudios.amex.language.service.LanguageService;
import net.twilightstudios.amex.util.persistence.TransactionManager;

import org.json.JSONException;

public class BasicLanguageServiceImpl implements LanguageService{

	private GeolocationService geolocationService;
	private LanguageDAO languageDAO;
	private LanguageExpressionDAO languageExpressionDAO;
	private TransactionManager manager;
	
	
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


	public TransactionManager getManager() {
		return manager;
	}


	public void setManager(TransactionManager manager) {
		this.manager = manager;
	}
	
	public LanguageExpressionDAO getLanguageExpressionDAO() {
		return languageExpressionDAO;
	}

	public void setLanguageExpressionDAO(LanguageExpressionDAO languageExpressionDAO) {
		this.languageExpressionDAO = languageExpressionDAO;
	}

	@Override
	public Language getLanguage(String city) throws JSONException, IOException {
		
		Country country = geolocationService.getCountry(city);
		manager.beginTransactionOnCurrentSession();
		Language language = null;
		try {
			language = languageDAO.getLanguageByStandardCode(country.getLanguage());
			manager.rollbackOnCurrentSession();
		}
		catch (Exception e) {
			manager.rollbackOnCurrentSession();
			throw new IOException(e);
		}
		
		return language;		
	}

	@Override
	public List<LanguageExpression> getLanguageExpressions(String originLanguage, String destinyLanguage) throws IOException {
		manager.beginTransactionOnCurrentSession();
		List<LanguageExpression> expressionsList = null;
		try {
			expressionsList = languageExpressionDAO.getByOriginDestinyLanguage(originLanguage, destinyLanguage);
			manager.rollbackOnCurrentSession();
		}
		catch (Exception e) {
			manager.rollbackOnCurrentSession();
			throw new IOException(e);
		}
		return expressionsList;
	}
	
	
}
