package com.example.moifoods.models;

import com.google.firebase.database.Exclude;

public class cart {
    private String amount;
    private String descrption;
    private String image;
    private String itemName;
    private String key;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public cart() {
    }

    private String price;

    public cart(String amount, String descrption, String image, String itemName, String price) {
        this.amount = amount;
        this.descrption = descrption;
        this.image = image;
        this.itemName = itemName;
        this.price = price;
    }
    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}
