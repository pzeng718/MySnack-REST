package com.example.mysnack_rest.model;

public class Product {
    private String id;
    private String name;
    private double price;
    private int qty;
    private String description;
    private String manufacturer;
    private String imageSrc;

    public String getId(){ return this.id; }

    public String getName() { return this.name; }

    public double getPrice() { return this.price; }

    public int getQty() { return qty; }

    public String getDescription() { return this.description; }

    public String getManufacturer() { return this.manufacturer; }

    public String getImageSrc() { return this.imageSrc; }

    public void setId(String id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setPrice(double price) { this.price = price; }

    public void setQty(int qty) { this.qty = qty; }

    public void setDescription(String description) { this.description = description; }

    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public void setImages(String imageSrc) { this.imageSrc = imageSrc; }
}
