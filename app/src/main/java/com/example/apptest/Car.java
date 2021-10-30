package com.example.apptest;

public class Car {
    private int id;
    private String model;
    private String color;
    private double dpl;
    private String imag;
    private String description;

    public Car(int id, String model, String color, double dpl, String imag, String description) {
        this.id = id;
        this.model = model;
        this.color = color;
        this.dpl = dpl;
        this.imag = imag;
        this.description = description;
    }

    public Car(String model, String color, double dpl, String imag, String description) {
        this.model = model;
        this.color = color;
        this.dpl = dpl;
        this.imag = imag;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getDpl() {
        return dpl;
    }

    public void setDpl(double dpl) {
        this.dpl = dpl;
    }

    public String getImag() {
        return imag;
    }

    public void setImag(String imag) {
        this.imag = imag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
