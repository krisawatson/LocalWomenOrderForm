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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "orders")
public class Orders {
    private Long id;
    private Long businessId;
    private Long userId;
    private List<OrderPart> orderParts = new ArrayList<>(0);

    public Orders() {
    	
    }
    
    public Orders(Long businessId, Long userId){
        this.businessId = businessId;
        this.userId = userId;
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
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        String value = "Orders [id="+ getId() + ", businessId="+getBusinessId()
                        +", userId="+getUserId()+ ",orderParts=[";
        for(OrderPart orderPart : getOrderParts()) {
            value += orderPart.toString();
        }
        value += "]]";
        return value;
    }
}
