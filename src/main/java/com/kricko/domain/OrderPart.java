package com.kricko.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "order_part")
public class OrderPart {
	private int id;
	private int month;
	private int year;
	private List<OrderPublication> publications = new ArrayList<>(0);
	private Orders orders;
	
	protected OrderPart(){
	}
	
	protected OrderPart(Orders orders, int month, int year, 
			List<OrderPublication> publications){
		this.orders = orders;
		this.month = month;
		this.year = year;
		this.publications = publications;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orderPart")
	public List<OrderPublication> getPublications() {
		return publications;
	}

	public void setPublications(List<OrderPublication> publications) {
		this.publications = publications;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orders_id", nullable = false)
	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}
}
