package net.twilightstudios.amex.util.rest.cache.dao;

import net.twilightstudios.amex.util.rest.cache.entity.CacheContent;

public interface CacheContentDAO {
	
	public CacheContent getCacheContent (String url);
	
	public void storeCacheContent (CacheContent content);
	
	public void deleteCacheContent (CacheContent content);

}
