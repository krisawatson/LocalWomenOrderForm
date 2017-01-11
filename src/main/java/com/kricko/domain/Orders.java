package com.kricko.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Orders {
	private int id;
	private int businessId;
	private List<OrderPart> orderParts = new ArrayList<>(0);
	
	protected Orders(){
	}
	
	public Orders(int busnessId) {
		this.businessId = busnessId;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getBusinessId() {
		return businessId;
	}

	public void setBusinessId(int businessId) {
		this.businessId = businessId;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orders")
	public List<OrderPart> getOrderParts() {
		return orderParts;
	}

	public void setOrderParts(List<OrderPart> orderParts) {
		this.orderParts = orderParts;
	}
}
