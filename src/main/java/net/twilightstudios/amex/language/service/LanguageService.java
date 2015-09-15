package net.twilightstudios.amex.language.service;

import java.io.IOException;

import net.twilightstudios.amex.language.entity.Language;

import org.json.JSONException;

public interface LanguageService {

	public Language getLanguage(String city) throws JSONException, IOException;

}
