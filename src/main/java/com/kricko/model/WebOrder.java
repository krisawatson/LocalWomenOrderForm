package com.kricko.model;

import java.util.List;

import com.kricko.domain.Business;
import com.kricko.domain.OrderPart;

public class WebOrder {

	private Business business;
	
	private List<OrderPart> orderParts;
	
	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public List<OrderPart> getOrderParts() {
		return orderParts;
	}
	
	public void setOrderParts(List<OrderPart> orderParts) {
		this.orderParts = orderParts;
	}
}
