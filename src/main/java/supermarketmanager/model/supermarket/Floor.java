package main.java.supermarketmanager.model.supermarket;


public class Floor extends MarketStructure<Aisle>{
    private int floor;

    public Floor(String name, int[] dimensions, int floor) {
        super(name, dimensions);
        this.floor = floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getFloor() {
        return floor;
    }
}
