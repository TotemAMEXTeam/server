package net.twilightstudios.amex.language.dao;

import java.util.List;

import net.twilightstudios.amex.language.entity.LanguageExpression;

public interface LanguageExpressionDAO {
	
	public List<LanguageExpression> getByOriginDestinyLanguage(String originLanguage, String destinyLanguage);

}
