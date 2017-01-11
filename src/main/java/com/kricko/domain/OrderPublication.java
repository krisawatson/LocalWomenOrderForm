package com.kricko.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "order_publication")
public class OrderPublication {

	private int id;
	private int adType;
	private int adSize;
	private String note;
	private OrderPart orderPart;
	
	protected OrderPublication(){
	}
	
	public OrderPublication(OrderPart orderPart, int adType, 
			int adSize, String note){
		this.orderPart = orderPart;
		this.adType = adType;
		this.adSize = adSize;
		this.note = note;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@NotNull
	public int getAdType() {
		return adType;
	}
	public void setAdType(int adType) {
		this.adType = adType;
	}
	@NotNull
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_part_id", nullable = false)
	public OrderPart getOrderPart() {
		return orderPart;
	}

	public void setOrderPart(OrderPart orderPart) {
		this.orderPart = orderPart;
	}
}
