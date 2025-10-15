package main.java.supermarketmanager.model.supermarket;

import java.text.DecimalFormat;

public class GoodItem {
    //figured it's easier to set these here, if you wanted to use imperial you'd only need to change this array
    public static String[] weightTypes = {"Kg", "g", "L", "mL"};
    public static String[] storageTypes = {"Unrefrigerated", "Refrigerated", "Frozen"};
    private String name, description, photoURL;
    private double price, weight;
    private int quantity;
    private int weightType = 0, storageType = 0;

    public GoodItem(String name, String description, double price, int quantity, double weight, int weightType, int storageType, String photoURL) {
        if(name != null && !name.isEmpty())
            if(name.length() < 31)
                this.name = name;
            else
                this.name = name.substring(0, 31);
        else this.name = "no_product_name";

        if(description != null && !description.isEmpty()) {
            description = description.trim();
            if (description.length() < 101)
                this.description = description;
            else
                this.description = description.substring(0, 101);
        }else this.description = "no_product_description";

        if(photoURL != null && !photoURL.isEmpty()){
            photoURL = photoURL.trim().toLowerCase();
            if(photoURL.contains("www")
            && (photoURL.contains(".jpg") || photoURL.contains(".png") || photoURL.contains(".gif") || photoURL.contains(".jpeg")))
                this.photoURL = photoURL;
        }else this.photoURL = "no_product_photo";

        if(price > 0) {
            DecimalFormat df = new DecimalFormat("#.##");
            this.price = Double.parseDouble(df.format(price));
        }else this.price = 1.00;

        if(quantity > 0)
            this.quantity = quantity;
        else this.quantity = 1;

        if(weight > 0)
            this.weight = weight;
        else this.weight = 1.00;

        if(storageType > -1 && storageType < storageTypes.length)
            this.storageType = storageType;

        if(weightType > -1 && weightType < weightTypes.length)
            this.weightType = weightType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name != null && !name.isEmpty())
            if(name.length() < 31)
                this.name = name;
            else
                this.name = name.substring(0, 31);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(description != null && !description.isEmpty()) {
            description = description.trim();
            if (description.length() < 101)
                this.description = description;
            else
                this.description = description.substring(0, 101);
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if(price > 0) {
            DecimalFormat df = new DecimalFormat("#.##");
            this.price = Double.parseDouble(df.format(price));
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if(quantity > 0)
            this.quantity = quantity;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        if(weight > 0)
            this.weight = weight;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        if(photoURL != null && !photoURL.isEmpty()){
            photoURL = photoURL.trim().toLowerCase();
            if(photoURL.contains("http") && (photoURL.contains(".jpg") || photoURL.contains(".png") || photoURL.contains(".gif") || photoURL.contains(".jpeg")))
                this.photoURL = photoURL;
        }
    }

    public String getStorageType() {
        return GoodItem.storageTypes[storageType];
    }

    public String getWeightType() {
        return GoodItem.weightTypes[weightType];
    }

    public String toString(){
        return "Product Name: " + name + "\n" +
                "Description: " + description + "\n" +
                "Price: " + price + "\n" +
                "Quantity: " + quantity + "\n" +
                "Weight: " + weight + getWeightType() + "\n" +
                "Storage Type: " + getStorageType() + "\n" +
                "Photo URL: " + photoURL + "\n";
    }
}
