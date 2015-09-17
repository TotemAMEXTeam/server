package net.twilightstudios.amex.language.dao.impl;

import net.twilightstudios.amex.language.dao.LanguageDAO;
import net.twilightstudios.amex.language.entity.Language;

public class BasicLanguageDAO implements LanguageDAO {


	@Override
	public Language getLanguageByStandardCode(String code) {
	
		if(code.equals("es")){
			
			return createEs();
		}
		else if(code.equals("en")){
			
			return createEn();
		}
		else{
			
			return createIt();
		}
	}

	private Language createEn(){
		
		Language language = new Language();
		language.setLanguage("Inglés");		
		language.setCode("en");
		
		return language;
	}

	private Language createEs(){
		
		Language language = new Language();
		language.setLanguage("Español");		
		language.setCode("es");
		
		return language;
	}

	private Language createIt(){
		
		Language language = new Language();
		language.setLanguage("Italiano");		
		language.setCode("it");
		
		return language;
	}

}
