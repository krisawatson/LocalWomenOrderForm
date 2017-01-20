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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "order_part")
public class OrderPart {
    private Long id;
    private int month;
    private int year;
    private List<OrderPublication> publications = new ArrayList<>(0);
    private Orders orders;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
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
    @JsonManagedReference
    public List<OrderPublication> getPublications() {
        return publications;
    }

    public void setPublications(List<OrderPublication> publications) {
        this.publications = publications;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id", nullable = false)
    @JsonBackReference
    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }
    
    @Override
    public String toString() {
        String value = "OrderPart [id="+ getId() + ", month="+getMonth()
                        +", year="+ getYear()+ ",orderPublications=[";
        for(OrderPublication publication : getPublications()) {
            value += publication.toString();
        }
        value += "]]";
        return value;
    }
}
