package main.java.supermarketmanager.model.supermarket;

public class Shelf extends MarketStructure<GoodItem>{
    int shelfNumber;

    public Shelf(String name, int[] dimensions, int shelfNumber) {
        super(name, dimensions);
        if(shelfNumber >= 0)
            this.shelfNumber = shelfNumber;
        else
            this.shelfNumber = -1;
    }

    public int getShelfNumber() {
        return shelfNumber;
    }

    public void setShelfNumber(int shelfNumber) {
        if(shelfNumber <= 0)
            this.shelfNumber = 0;
    }

    @Override
    public String objectDetails() {
        return "";
    }

    @Override
    public String toString() {
        return "";
    }
}
