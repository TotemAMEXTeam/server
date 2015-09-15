package net.twilightstudios.amex.language.rest;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;

import net.twilightstudios.amex.language.entity.Language;
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
	
	@RequestMapping(value="/retrieve", method=RequestMethod.GET, produces={"application/json"})
	public Language retrieveLanguage(@RequestParam String city) throws IOException, JSONException {
		try {
			return languageService.getLanguage(city);			
		} catch (Exception e) {
			 throw new WebApplicationException(e);
		}
	}

	public LanguageService getLanguageService() {
		return languageService;
	}

	public void setLanguageService(LanguageService languageService) {
		this.languageService = languageService;
	}
}
