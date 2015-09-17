package net.twilightstudios.amex.language.dao.impl;

import org.hibernate.Session;

import net.twilightstudios.amex.language.dao.LanguageDAO;
import net.twilightstudios.amex.language.entity.Language;
import net.twilightstudios.amex.util.persistence.SessionManager;

public class HibernateLanguageDAO implements LanguageDAO {
	
	private SessionManager manager;

	public SessionManager getManager() {
		return manager;
	}

	public void setManager(SessionManager manager) {
		this.manager = manager;
	}

	@Override
	public Language getLanguageByStandardCode(String code) {
		Session session = manager.getCurrentSession();
		return (Language) session.get(Language.class, code);
	}
	
	

}
