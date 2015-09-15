package net.twilightstudios.amex.language.dao.impl;

import net.twilightstudios.amex.language.dao.LanguageDAO;
import net.twilightstudios.amex.language.entity.Language;

public class BasicLanguageDAO implements LanguageDAO {

	@Override
	public Language getLanguageById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

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
		language.setId(1l);
		language.setDescription("Inglés");		
		language.setStandardCode("en");
		
		return language;
	}

	private Language createEs(){
		
		Language language = new Language();
		language.setId(0l);
		language.setDescription("Español");		
		language.setStandardCode("es");
		
		return language;
	}

	private Language createIt(){
		
		Language language = new Language();
		language.setId(2l);
		language.setDescription("Italiano");		
		language.setStandardCode("it");
		
		return language;
	}

}
