package com.example.shortenlinks.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.util.Date;

@Entity
public class Link {

    private Long id;
    private String shortenLink;
    private String actualLink;
    private Date created;

    public Link(String shortenLink, String actualLink) {
        this.shortenLink = shortenLink;
        this.actualLink = actualLink;
    }

    public Link() {

    }

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public String getShortenLink() {
        return shortenLink;
    }

    public void setShortenLink(String shortenLink) {
        this.shortenLink = shortenLink;
    }

    public String getActualLink() {
        return actualLink;
    }

    public void setActualLink(String actualLink) {
        this.actualLink = actualLink;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
