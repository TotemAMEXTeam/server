package net.twilightstudios.amex.geo.dao;

import net.twilightstudios.amex.geo.entity.City;
import net.twilightstudios.amex.geo.entity.Country;

public interface CountryDao {

	public Country getCountryById(Long id);
	public Country getCountryByStandardCode(String code);
	
}
