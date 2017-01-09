package com.kricko.model;

public class OrderPublication {

	private int id;
	private int advert;
	private int adSize;
	private String note;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAdvert() {
		return advert;
	}
	public void setAdvert(int advert) {
		this.advert = advert;
	}
	public int getAdSize() {
		return adSize;
	}
	public void setAdSize(int adSize) {
		this.adSize = adSize;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
}
