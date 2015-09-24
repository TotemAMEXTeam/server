package net.twilightstudios.amex.geo.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import net.twilightstudios.amex.geo.dao.CountryDao;
import net.twilightstudios.amex.geo.entity.City;
import net.twilightstudios.amex.geo.entity.Country;
import net.twilightstudios.amex.language.entity.Language;
import net.twilightstudios.amex.util.persistence.SessionManager;

public class HibernateCountryDAO implements CountryDao {

	private SessionManager manager;

	public SessionManager getManager() {
		return manager;
	}

	public void setManager(SessionManager manager) {
		this.manager = manager;
	}
	
	@Override
	public Country getCountryById(Long id) {
		Session session = manager.getCurrentSession();
		return (Country) session.get(Country.class, id);
	}

	@Override
	public Country getCountryByStandardCode(String code) {
		Session session = manager.getCurrentSession();
		Criteria criteria = session.createCriteria(Country.class);
		criteria.add(Restrictions.eq("standardCode", code));
		return (Country)criteria.uniqueResult();
	}

}
