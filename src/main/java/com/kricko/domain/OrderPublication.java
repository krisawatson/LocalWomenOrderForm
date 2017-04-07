package com.kricko.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "order_publication")
public class OrderPublication {

    private Long id;
    private Long adType;
    private Long adSize;
    private Long publicationId;
    private String note;
    private OrderPart orderPart;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public Long getAdType() {
        return adType;
    }

    public void setAdType(Long adType) {
        this.adType = adType;
    }

    @NotNull
    public Long getAdSize() {
        return adSize;
    }

    public void setAdSize(Long adSize) {
        this.adSize = adSize;
    }

    @NotNull
    public Long getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(Long publicationId) {
        this.publicationId = publicationId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_part_id", nullable = false)
    @JsonBackReference
    public OrderPart getOrderPart() {
        return orderPart;
    }

    public void setOrderPart(OrderPart orderPart) {
        this.orderPart = orderPart;
    }

    @Override
    public String toString() {
        return "OrderPublication [id=" + getId() + ", adSize=" + getAdSize()
                + ", adType=" + getAdType() + ", note=" + getNote()
                + ", publicationId=" + getPublicationId() + "]]";
    }
}
