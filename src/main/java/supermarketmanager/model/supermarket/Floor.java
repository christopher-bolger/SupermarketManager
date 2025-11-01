package supermarketmanager.model.supermarket;


public class Floor extends MarketStructure<Aisle>{
    private int floor;

    public Floor(String name, int[] dimensions, int floor) {
        super(name, dimensions);
        this.floor = floor;
    }

    public Floor(String name){
        super(name, new int[] {1,1});
        floor = 0;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getFloor() {
        return floor;
    }

    @Override
    public boolean replace(Aisle itemToReplace, Aisle item) {
        if(list.contains(itemToReplace))
            return list.set(list.indexOf(itemToReplace), item) == itemToReplace;
        return false;
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
