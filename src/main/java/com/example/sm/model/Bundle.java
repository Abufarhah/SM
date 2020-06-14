package com.example.sm.model;

public class Bundle implements Comparable<Bundle>{
    private int id;
    private String name;
    private double price;

    public Bundle() {
    }

    public Bundle(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
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

    @Override
    public String toString() {
        return "Bundle [id=" + id + ", name=" + name + ", price=" + price + "]";
    }

    @Override
    public int compareTo(Bundle o) {
        if(getId()>o.getId())return 1;
        else if(getId()<o.getId())return -1;
        return 0;
    }

}
