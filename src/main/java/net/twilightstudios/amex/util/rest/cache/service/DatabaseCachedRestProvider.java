package net.twilightstudios.amex.util.rest.cache.service;

import java.io.IOException;
import java.util.Date;

import net.twilightstudios.amex.util.persistence.TransactionManager;
import net.twilightstudios.amex.util.rest.RestProvider;
import net.twilightstudios.amex.util.rest.cache.dao.CacheContentDAO;
import net.twilightstudios.amex.util.rest.cache.entity.CacheContent;

public class DatabaseCachedRestProvider implements RestProvider {
	
	private RestProvider delegate;
	private long cacheTTL;
	private TransactionManager manager;
	private CacheContentDAO dao;
	
	
	public TransactionManager getManager() {
		return manager;
	}

	public void setManager(TransactionManager manager) {
		this.manager = manager;
	}

	public CacheContentDAO getDao() {
		return dao;
	}

	public void setDao(CacheContentDAO dao) {
		this.dao = dao;
	}

	public RestProvider getDelegate() {
		return delegate;
	}

	public void setDelegate(RestProvider delegate) {
		this.delegate = delegate;
	}
	
	public long getCacheTTL() {
		return cacheTTL;
	}

	public void setCacheTTL(long cacheTTL) {
		this.cacheTTL = cacheTTL;
	}

	public DatabaseCachedRestProvider(RestProvider delegate) {
		this.delegate = delegate;
	}

	@Override
	public String retrieveRawInformation(String urlString) throws IOException {
		manager.beginTransactionOnCurrentSession();
		try {	
			CacheContent content = dao.getCacheContent(urlString);
			if(content != null){		
				if(System.currentTimeMillis() - content.getTimestamp().getTime() <= cacheTTL){	
					manager.rollbackOnCurrentSession();
					return content.getJson();
				}
				else {
					dao.deleteCacheContent(content);
				}
			}
					
			String result = delegate.retrieveRawInformation(urlString);
			
			content = new CacheContent(urlString, new Date(),result); 
			dao.storeCacheContent(content);
			manager.commitOnCurrentSession();
			return result;
		}
		catch (Exception e) {
			manager.rollbackOnCurrentSession();
			throw new IOException(e);
		}
	}

	@Override
	public byte[] retrieveRawImage(String urlString) throws IOException {
		return delegate.retrieveRawImage(urlString);
	}

}
