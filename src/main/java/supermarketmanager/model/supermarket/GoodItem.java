package supermarketmanager.model.supermarket;

import java.text.DecimalFormat;
import java.util.Objects;

public class GoodItem extends MarketStructure<GoodItem>{
    //figured it's easier to set these here, if you wanted to use imperial you'd only need to change this array
    public static String[] weightTypes = {"Kg", "g", "L", "mL"};
    public static String[] storageTypes = {"Unrefrigerated", "Refrigerated", "Frozen"};
    private String description, photoURL;
    private double price, weight;
    private int quantity;
    private int weightType = 0, storageType = 0;

    public GoodItem(String name, String description, double price, int quantity, double weight, int weightType, int storageType, String photoURL) {
        super(name, new int[] {1,1});
        if(description != null && !description.isEmpty()) {
            description = description.trim();
            if (description.length() < 101)
                this.description = description;
            else
                this.description = description.substring(0, 101);
        }else this.description = "no_product_description";

        if(photoURL != null && !photoURL.isEmpty()){
            photoURL = photoURL.trim().toLowerCase();
            if(photoURL.contains("http")
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

    public GoodItem(String name, String description) {
        super(name, new int[] {1,1});

        if(description != null && !description.isEmpty()) {
            description = description.trim();
            if (description.length() < 101)
                this.description = description;
            else
                this.description = description.substring(0, 101);
        }else this.description = "no_product_description";

        photoURL = "no_product_photo";
        price = weight = 1;
        quantity = weightType = storageType = 0;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GoodItem goodItem)) return false;
        return super.getName().equalsIgnoreCase(goodItem.getName()) && description.equalsIgnoreCase(goodItem.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getName(), description);
    }

    @Override
    public boolean replace(GoodItem itemToReplace, GoodItem item) {
        return false;
    }

    @Override
    public String details() {
        return "Product Name: " + super.getName() + "\n" +
                "Description: " + description + "\n" +
                "Price: " + price + "\n" +
                "Quantity: " + quantity + "\n" +
                "Total Value: " + totalValue() + "\n" +
                "Weight: " + weight + getWeightType() + "\n" +
                "Storage Type: " + getStorageType() + "\n" +
                "Photo URL: " + photoURL + "\n";
    }

    public double totalValue(){
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(price * quantity));
    }

    public String getStorageType() {
        return GoodItem.storageTypes[storageType];
    }

    public void setStorageType(int index){
        if(index > -1 && index < storageTypes.length)
            storageType = index;
    }

    public String getWeightType() {
        return GoodItem.weightTypes[weightType];
    }

    public String toString() {
        return "Product Name: " + super.getName() + " \t" + "Price: " + price + " \t" + "Quantity: " + quantity;
    }
}
