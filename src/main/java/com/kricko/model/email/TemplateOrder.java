package com.kricko.model.email;

import java.util.List;

public class TemplateOrder {
    private List<TemplateOrderPart> orderParts;

    public List<TemplateOrderPart> getOrderParts() {
        return orderParts;
    }

    public void setOrderParts(List<TemplateOrderPart> orderParts) {
        this.orderParts = orderParts;
    }
}
