package com.kricko.model;

import java.util.List;

public class OrderPart {

	private int id;
	private int month;
	private int year;
	private List<OrderPublication> publications;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public List<OrderPublication> getPublications() {
		return publications;
	}
	public void setPublications(List<OrderPublication> publications) {
		this.publications = publications;
	}
}
