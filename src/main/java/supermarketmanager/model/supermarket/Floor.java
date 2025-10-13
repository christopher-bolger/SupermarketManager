package main.java.supermarketmanager.model.supermarket;


public class Floor extends MarketStructure{
    private final int[] dimensions = new int[2];

    public Floor(String name, int[] dimensions) {
        super(name);
        if(dimensions.length != 2){
            dimensions[0] = 1;
            dimensions[1] = 1;
        }else{
            this.dimensions[0] = dimensions[0];
            this.dimensions[1] = dimensions[1];
        }
    }
}
