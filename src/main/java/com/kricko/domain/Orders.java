package com.kricko.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Orders {
    private Long id;
    private Long businessId;
    private Long userId;
    private double priceIncVat;
    private double priceExVat;
    private double deposit;
    private String customerSignature;
    private String userSignature;
    private Date created;
    private Date updated;
    private List<OrderPart> orderParts = new ArrayList<>(0);

    public Orders() {

    }

    public Orders(Long businessId, Long userId, double priceExVat, double priceIncVat,
                  double deposit, String customerSignature, String userSignature) {
        this.businessId = businessId;
        this.userId = userId;
        this.priceExVat = priceExVat;
        this.priceIncVat = priceIncVat;
        this.deposit = deposit;
        this.customerSignature = customerSignature;
        this.userSignature = userSignature;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    @NotNull
    private Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "price_inc_vat", nullable = false, columnDefinition = "Decimal(10,2) default '0.00'")
    public double getPriceIncVat() {
        return priceIncVat;
    }

    public void setPriceIncVat(double priceIncVat) {
        this.priceIncVat = priceIncVat;
    }

    @Column(name = "price_ex_vat", nullable = false, columnDefinition = "Decimal(10,2) default '0.00'")
    public double getPriceExVat() {
        return priceExVat;
    }

    public void setPriceExVat(double priceExVat) {
        this.priceExVat = priceExVat;
    }

    @Column(name = "deposit", nullable = false, columnDefinition = "Decimal(10,2) default '0.00'")
    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    @Lob
    public String getCustomerSignature() {
        return customerSignature;
    }

    public void setCustomerSignature(String customerSignature) {
        this.customerSignature = customerSignature;
    }

    @Lob
    public String getUserSignature() {
        return userSignature;
    }

    public void setUserSignature(String userSignature) {
        this.userSignature = userSignature;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orders")
    @JsonManagedReference
    public List<OrderPart> getOrderParts() {
        return orderParts;
    }

    public void setOrderParts(List<OrderPart> orderParts) {
        this.orderParts = orderParts;
    }

    @Override
    public String toString() {
        StringBuilder value = new StringBuilder("Orders [id=" + getId() + ", businessId=" + getBusinessId()
                + ", userId=" + getUserId() + ",orderParts=[");
        for (OrderPart orderPart : getOrderParts()) {
            value.append(orderPart.toString());
        }
        value.append("]]");
        return value.toString();
    }
}
