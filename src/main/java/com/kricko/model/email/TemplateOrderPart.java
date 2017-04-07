package com.kricko.model.email;

import java.util.ArrayList;
import java.util.List;

public class TemplateOrderPart {
    private Long id;
    private String month;
    private int year;
    private List<TemplateOrderPublication> publications = new ArrayList<>(0);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<TemplateOrderPublication> getPublications() {
        return publications;
    }

    public void setPublications(List<TemplateOrderPublication> publications) {
        this.publications = publications;
    }
}
