package com.kricko.model;

import java.util.List;

public class Order {
	private Business business;
	private List<OrderPart> orders;
	
	public Business getBusiness() {
		return business;
	}
	public void setBusiness(Business business) {
		this.business = business;
	}
	public List<OrderPart> getOrders() {
		return orders;
	}
	public void setOrders(List<OrderPart> orders) {
		this.orders = orders;
	}
}
