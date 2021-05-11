package com.marcanta.ged.dtos;

import com.marcanta.ged.models.Image;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Optional;

public class ImageGetDto {

    public static final String BASE_URL = "http://localhost:8080/api";

    private Long id;

    private String name;

    private String description;

    private Date date;

    private boolean published;

    private String category;

    private String objects;

    private int nbPersons;

    private String imageUrl;

    public ImageGetDto(Long id, String name, String description, Date date, boolean published, String category, String objects, int nbPersons, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.published = published;
        this.category = category;
        this.objects = objects;
        this.nbPersons = nbPersons;
        this.imageUrl = imageUrl;
    }

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static ImageGetDto fromImage(Image image) {
        return new ImageGetDto(image.getId(), image.getName(), image.getDescription(), image.getDate(), image.isPublished(), image.getCategory(), image.getObjects(), image.getNbPersons(), ImageGetDto.BASE_URL + "/images/" + image.getId() + "/view");
    }
}
