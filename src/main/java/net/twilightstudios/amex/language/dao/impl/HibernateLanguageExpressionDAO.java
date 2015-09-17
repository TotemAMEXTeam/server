package net.twilightstudios.amex.language.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import net.twilightstudios.amex.language.dao.LanguageExpressionDAO;
import net.twilightstudios.amex.language.entity.LanguageExpression;
import net.twilightstudios.amex.util.persistence.SessionManager;

public class HibernateLanguageExpressionDAO implements LanguageExpressionDAO {
	
	private SessionManager manager;

	public SessionManager getManager() {
		return manager;
	}
	
	public void setManager(SessionManager manager) {
		this.manager = manager;
	}

	@Override
	public List<LanguageExpression> getByOriginDestinyLanguage(String originLanguage,
			String destinyLanguage) {
		
		Session session = manager.getCurrentSession();
		Criteria criteria = session.createCriteria(LanguageExpression.class);
		criteria.add(Restrictions.eq("originLanguage", originLanguage));
		criteria.add(Restrictions.eq("destinyLanguage", destinyLanguage));
		
		return (List<LanguageExpression>)criteria.list();
		
	}

}
