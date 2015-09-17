package net.twilightstudios.amex.language.rest;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.WebApplicationException;

import net.twilightstudios.amex.language.entity.Language;
import net.twilightstudios.amex.language.entity.LanguageExpression;
import net.twilightstudios.amex.language.service.LanguageService;

import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/language")
public class LanguageRestService {

	private LanguageService languageService;
	
	public LanguageService getLanguageService() {
		return languageService;
	}

	public void setLanguageService(LanguageService languageService) {
		this.languageService = languageService;
	}
	
	@RequestMapping(value="/retrieve", method=RequestMethod.GET, produces={"application/json"})
	public Language retrieveLanguage(@RequestParam String city) throws IOException, JSONException {
		try {
			return languageService.getLanguage(city);			
		} catch (Exception e) {
			 throw new WebApplicationException(e);
		}
	}
	
	@RequestMapping(value="/retrieveExpressions", method=RequestMethod.GET, produces={"application/json"})
	public List<LanguageExpression> retrieveLanguage(@RequestParam String originLanguage, String destinyLanguage) throws IOException, JSONException {
		try {
			return languageService.getLanguageExpressions(originLanguage, destinyLanguage);			
		} catch (Exception e) {
			 throw new WebApplicationException(e);
		}
	}

}
