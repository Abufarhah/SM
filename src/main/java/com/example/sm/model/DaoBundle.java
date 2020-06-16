package com.example.sm.model;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class DaoBundle {

    private int id;
    private String name;
    private double price;
    private String creationDate;
    private String updatingDate;



    public DaoBundle() {
    }

    public DaoBundle(int id, String name, double price,String creationDate,String updatingDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.creationDate=creationDate;
        this.updatingDate=updatingDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getUpdatingDate() {
        return updatingDate;
    }

    public void setUpdatingDate(String updatingDate) {
        this.updatingDate = updatingDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate=creationDate.toString();
    }

    public void setUpdatingDate(Date updatingDate) {
        this.updatingDate = updatingDate.toString();
    }

}
