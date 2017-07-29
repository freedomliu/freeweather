package com.simple.freedom.beans;

public class AreaSizeBean{
	private int id;
	private String areaName;
	private double longitudeMin;
	private double longitudeMax;
	private double latitudeMin;
	private double latitudeMax;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public double getLongitudeMin() {
		return longitudeMin;
	}
	public void setLongitudeMin(double longitudeMin) {
		this.longitudeMin = longitudeMin;
	}
	public double getLongitudeMax() {
		return longitudeMax;
	}
	public void setLongitudeMax(double longitudeMax) {
		this.longitudeMax = longitudeMax;
	}
	public double getLatitudeMin() {
		return latitudeMin;
	}
	public void setLatitudeMin(double latitudeMin) {
		this.latitudeMin = latitudeMin;
	}
	public double getLatitudeMax() {
		return latitudeMax;
	}
	public void setLatitudeMax(double latitudeMax) {
		this.latitudeMax = latitudeMax;
	}
}
