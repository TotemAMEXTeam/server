package net.twilightstudios.amex.util.rest.cache.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import net.twilightstudios.amex.util.persistence.SessionManager;
import net.twilightstudios.amex.util.rest.cache.dao.ImageCacheContentDAO;
import net.twilightstudios.amex.util.rest.cache.entity.ImageCacheContent;

public class HibernateImageCacheContentDAO implements ImageCacheContentDAO {
	
	private SessionManager manager;

	public SessionManager getManager() {
		return manager;
	}

	public void setManager(SessionManager manager) {
		this.manager = manager;
	}

	@Override
	public ImageCacheContent getImageCacheContent(String url) {
		Session session = manager.getCurrentSession();
		Criteria criteria = session.createCriteria(ImageCacheContent.class);
		criteria.add(Restrictions.eq("url", url));
		return (ImageCacheContent)criteria.uniqueResult();
	}

	@Override
	public void storeImageCacheContent(ImageCacheContent content) {
		Session session = manager.getCurrentSession();
		session.save(content);
	}

	@Override
	public void deleteImageCacheContent(ImageCacheContent content) {
		Session session = manager.getCurrentSession();
		session.delete(content);
	}

}
