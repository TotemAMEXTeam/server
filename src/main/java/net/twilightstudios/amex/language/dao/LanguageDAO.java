package net.twilightstudios.amex.language.dao;

import net.twilightstudios.amex.language.entity.Language;

public interface LanguageDAO {

	public Language getLanguageByStandardCode(String code);

}
