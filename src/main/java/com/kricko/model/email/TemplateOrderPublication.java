package com.kricko.model.email;

import com.kricko.domain.Publication;

public class TemplateOrderPublication {
	private Long id;
    private String adType;
    private String adSize;
    private Publication publication;
    private String note;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAdType() {
		return adType;
	}
	public void setAdType(String adType) {
		this.adType = adType;
	}
	public String getAdSize() {
		return adSize;
	}
	public void setAdSize(String adSize) {
		this.adSize = adSize;
	}
	public Publication getPublication() {
		return publication;
	}
	public void setPublication(Publication publication) {
		this.publication = publication;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
}
