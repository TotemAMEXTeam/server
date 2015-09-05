package net.twilightstudios.amex.geo.dao;

import net.twilightstudios.amex.geo.entity.City;
import net.twilightstudios.amex.geo.entity.Country;
import net.twilightstudios.amex.geo.entity.Language;

public interface CountryDao {

	public Country getCountryById(Long id);
	public City getCityById(Long id);
	public Language getLanguageById(Long id);
	
}
