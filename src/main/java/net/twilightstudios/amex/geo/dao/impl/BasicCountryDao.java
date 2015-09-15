package net.twilightstudios.amex.geo.dao.impl;

import net.twilightstudios.amex.geo.dao.CountryDao;
import net.twilightstudios.amex.geo.entity.City;
import net.twilightstudios.amex.geo.entity.Country;

public class BasicCountryDao implements CountryDao {

	@Override
	public Country getCountryById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Country getCountryByStandardCode(String code) {

		if(code.equals("ES")){
			
			return createSpain();
		}
		else if(code.equals("US")){
			
			return createUSA();
		}
		else if(code.equals("GB")){
			
			return createGB();
		}
		else{
			
			return createITA();
		}
	}

	@Override
	public City getCityById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	private Country createSpain(){
		
		Country sp = new Country();
		sp.setId(0l);
		sp.setLanguage("es");
		sp.setName("España");
		sp.setStandardCode("ES");
		
		return sp;
	}
	
	private Country createUSA(){
		
		Country sp = new Country();
		sp.setId(1l);
		sp.setLanguage("en");
		sp.setName("United States");
		sp.setStandardCode("US");
		
		return sp;
	}
	
	private Country createGB(){
		
		Country sp = new Country();
		sp.setId(2l);
		sp.setLanguage("en");
		sp.setName("United Kingdom");
		sp.setStandardCode("GB");
		
		return sp;
	}
	
	private Country createITA(){
		
		Country sp = new Country();
		sp.setId(3l);
		sp.setLanguage("it");
		sp.setName("Italia");
		sp.setStandardCode("IT");
		
		return sp;
	}
}
