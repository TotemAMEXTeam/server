package net.twilightstudios.amex.util.rest.cache.entity;

import java.util.Date;

public class ImageCacheContent {
	
	private Long id;
	private String url;
	private Date timestamp;
	private byte[] image;
	
	public ImageCacheContent() { }
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	
	

}
