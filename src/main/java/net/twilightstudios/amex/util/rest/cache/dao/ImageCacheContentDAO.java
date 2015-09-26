package net.twilightstudios.amex.util.rest.cache.dao;

import net.twilightstudios.amex.util.rest.cache.entity.ImageCacheContent;

public interface ImageCacheContentDAO {
	
	public ImageCacheContent getImageCacheContent (String url);
	
	public void storeImageCacheContent (ImageCacheContent content);
	
	public void deleteImageCacheContent (ImageCacheContent content);

}
