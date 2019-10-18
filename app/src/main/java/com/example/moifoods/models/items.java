package com.example.moifoods.models;

import com.google.firebase.database.Exclude;

public class items {



    public items(String image, String itemName, String descrption, String amount) {
        this.image = image;
        ItemName = itemName;
        this.descrption = descrption;
        Amount = amount;
    }

    private String image;
    private String key;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private String ItemName;
    private String descrption;
    private String Amount;

    public items(String image,String itemName, String descrption, String amount, String price) {
        this.image = image;
        ItemName = itemName;
        this.descrption = descrption;
        Amount = amount;
        this.price = price;
    }

    private String price;

    public items() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
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
