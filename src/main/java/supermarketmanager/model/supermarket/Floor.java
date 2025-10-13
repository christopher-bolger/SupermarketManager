package main.java.supermarketmanager.model.supermarket;


public class Floor extends MarketStructure<Aisle>{
    private final int[] dimensions = new int[2];

    public Floor(String name, int[] dimensions) {
        super(name, dimensions);
    }

    public String toString(){
        return super.toString() + "\nFloor Dimensions: " + dimensions[0] + ", " + dimensions[1] + ".";
    }
}
