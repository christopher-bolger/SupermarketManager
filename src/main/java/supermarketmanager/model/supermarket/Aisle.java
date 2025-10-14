package main.java.supermarketmanager.model.supermarket;

public class Aisle extends MarketStructure<Shelf>{
    int[] aisleSize = new int[2];

    public Aisle(String name, int[] dimensions, int[] maxSize) {
        super(name, dimensions);
        if(aisleSize.length != 2
        && (maxSize[0] < aisleSize[0] || maxSize[1] < aisleSize[1])){
            this.aisleSize[0] = 1;
            this.aisleSize[1] = 1;
        }else{
            this.aisleSize[0] = dimensions[0];
            this.aisleSize[1] = dimensions[1];
        }
    }

    public void setAisleSize(int[] newSize, int[] maxSize){
        if(aisleSize.length == 2
        && maxSize[0] > aisleSize[0]
        && maxSize[1] > aisleSize[1]){
            aisleSize[0] = newSize[0];
            aisleSize[1] = newSize[1];
        }
    }

    public int[] getAisleSize(){
        return aisleSize;
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
