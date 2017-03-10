package com.kricko.model;

import java.util.List;

import com.kricko.domain.Business;
import com.kricko.domain.OrderPart;

public class WebOrder {

	private Business business;
	private List<OrderPart> orderParts;
	private Double priceExVat;
	private Double priceIncVat;
	private Double deposit;
	private String customerSignature;
	private String userSignature;
	
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

    public Double getPriceExVat() {
        return priceExVat;
    }

    public void setPriceExVat(Double priceExVat) {
        this.priceExVat = priceExVat;
    }

    public Double getPriceIncVat() {
        return priceIncVat;
    }

    public void setPriceIncVat(Double priceIncVat) {
        this.priceIncVat = priceIncVat;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public String getCustomerSignature() {
        return customerSignature;
    }

    public void setCustomerSignature(String customerSignature) {
        this.customerSignature = customerSignature;
    }

    public String getUserSignature() {
        return userSignature;
    }

    public void setUserSignature(String userSignature) {
        this.userSignature = userSignature;
    }
}
