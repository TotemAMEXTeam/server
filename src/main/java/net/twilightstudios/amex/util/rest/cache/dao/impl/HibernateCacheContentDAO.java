package net.twilightstudios.amex.util.rest.cache.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import net.twilightstudios.amex.util.persistence.SessionManager;
import net.twilightstudios.amex.util.rest.cache.dao.CacheContentDAO;
import net.twilightstudios.amex.util.rest.cache.entity.CacheContent;

public class HibernateCacheContentDAO implements CacheContentDAO {
	
	private SessionManager manager;

	public SessionManager getManager() {
		return manager;
	}

	public void setManager(SessionManager manager) {
		this.manager = manager;
	}

	@Override
	public CacheContent getCacheContent(String url) {
		Session session = manager.getCurrentSession();
		Criteria criteria = session.createCriteria(CacheContent.class);
		criteria.add(Restrictions.eq("url", url));
		return (CacheContent)criteria.uniqueResult();
	}

	@Override
	public void storeCacheContent(CacheContent content) {
		Session session = manager.getCurrentSession();
		session.save(content);
	}

	@Override
	public void deleteCacheContent(CacheContent content) {
		Session session = manager.getCurrentSession();
		session.delete(content);
	}

}
