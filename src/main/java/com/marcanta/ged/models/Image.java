package com.marcanta.ged.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="IMAGE")
public class Image implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "path")
    private String path;

    @Column(name = "date")
    private Date date;

    @Column(name = "published")
    private boolean published;

    @Column(name = "category")
    private String category;

    @Column(name = "objects")
    private String objects;

    @Column(name = "nb_persons")
    private int nbPersons;

    @Column(name = "achived")
    private boolean archived;

    public Image(String name, String description, String path, boolean published, String category, String objects, int nbPersons) {
        this.name = name;
        this.description = description;
        this.path = path;
        this.date = new Date();
        this.published = published;
        this.category = category;
        this.objects = objects;
        this.nbPersons = nbPersons;
        this.archived = false;
    }

    public Image() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getObjects() {
        return objects;
    }

    public void setObjects(String objects) {
        this.objects = objects;
    }

    public int getNbPersons() {
        return nbPersons;
    }

    public void setNbPersons(int nbPersons) {
        this.nbPersons = nbPersons;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
