package net.twilightstudios.amex.language.service;

import java.io.IOException;
import java.util.List;

import net.twilightstudios.amex.language.entity.Language;
import net.twilightstudios.amex.language.entity.LanguageExpression;

import org.json.JSONException;

public interface LanguageService {

	public Language getLanguage(String city) throws JSONException, IOException;
	
	public List<LanguageExpression> getLanguageExpressions (String originLanguage, String destinyLanguage);

}
