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

    @Override
    public String details() {
        StringBuilder string = new StringBuilder();
        int[] dimensions = getDimensions();
        string.append("Floor: ").append(floor).append("\t")
            .append("Name: ").append(super.getName()).append("\t")
            .append("Size: ").append(dimensions[0]).append(", ").append(dimensions[1]).append("\n");
        return string.toString();
    }
}
