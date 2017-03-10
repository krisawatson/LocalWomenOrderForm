package com.kricko.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "publication")
public class Publication {
    private Long id;
    private String name;
    private String email;
    private String photoshootEmail;
    private boolean enabled;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @NotNull
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhotoshootEmail() {
        return photoshootEmail;
    }
    public void setPhotoshootEmail(String photoshootEmail) {
        this.photoshootEmail = photoshootEmail;
    }
    @Column(columnDefinition="tinyint(1) default 1")
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
