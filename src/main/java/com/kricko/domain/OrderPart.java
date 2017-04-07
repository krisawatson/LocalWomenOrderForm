/*
 * Kris Watson Copyright (c) 2017.
 */

package com.kricko.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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
        StringBuilder value = new StringBuilder("OrderPart [id=" + getId() + ", month=" + getMonth()
                + ", year=" + getYear() + ",orderPublications=[");
        for (OrderPublication publication : getPublications()) {
            value.append(publication.toString());
        }
        value.append("]]");
        return value.toString();
    }
}
