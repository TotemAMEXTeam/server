package net.twilightstudios.amex.util.rest.cache.entity;

import java.util.Date;

public class CacheContent {
	
	private Long id;
	private String url;
	private Date timestamp;
	private String json;
	
	public CacheContent() {	}
	
	public CacheContent(String url, Date timestamp, String json) {
		super();
		this.timestamp = timestamp;
		this.json = json;
		this.url = url;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTimestamp() {
		return timestamp;
	}
	
	public String getJson() {
		return json;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
	

}
