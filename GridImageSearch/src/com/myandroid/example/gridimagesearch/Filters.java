package com.myandroid.example.gridimagesearch;

import java.io.Serializable;

public class Filters implements Serializable {

	private static final long serialVersionUID = 3751675783879116953L;
	private String size;
	private String color;
	private String type;
	private String site;
	
	public Filters(String size, String color, String type, String siteFilter) {
		this.size = size;
		this.color = color;
		this.type = type;
		this.site = siteFilter;
	}
	
	public String getSizeFilter() {
		return size;
	}
	
	public String getColorFilter() {
		return color;
	}
	
	public String getTypeFilter() {
		return type;
	}
	
	public String getSiteFilter() {
		return site;
	}
	
	@Override
	public String toString() {
		return "Size=" + size + ",Color=" + color + ",Type=" + type + ",Site="
				+ site;
	}
	
}
