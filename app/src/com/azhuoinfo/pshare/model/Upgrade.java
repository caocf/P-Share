package com.azhuoinfo.pshare.model;

import java.io.Serializable;

import mobi.cangol.mobile.parser.Element;

public class Upgrade implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 0L;
	private String url;
	private String version;
	private String desc;
	@Element("min_version")
	private String minVersion;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getMinVersion() {
		return minVersion;
	}
	public void setMinVersion(String minVersion) {
		this.minVersion = minVersion;
	}
	@Override
	public String toString() {
		return "Upgrade [url=" + url + ", version=" + version + ", desc="
				+ desc + ", minVersion=" + minVersion + "]";
	}
	
	
}
