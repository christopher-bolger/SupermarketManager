package main.java.supermarketmanager.model.supermarket;

public class Aisle extends MarketStructure<Shelf>{
    int[] aisleSize = new int[2];
    public static String[] storageTypes = {"Unrefrigerated", "Refrigerated", "Frozen"};
    int storageType = 0;

    public Aisle(String name, int[] dimensions, int storageType) {
        super(name, dimensions);
        if(aisleSize.length != 2){
            this.aisleSize[0] = 1;
            this.aisleSize[1] = 1;
        }else{
            this.aisleSize[0] = dimensions[0];
            this.aisleSize[1] = dimensions[1];
        }
        if(storageType > 0 && storageType < storageTypes.length)
            this.storageType = storageType;
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

    public String getStorageType(){
        return storageTypes[storageType];
    }

    @Override
    public String details() {
        StringBuilder string = new StringBuilder();
        int[] dimensions = getDimensions();
        string.append("Name: ").append(super.getName()).append("\t")
                .append("Size: ").append(dimensions[0]).append(", ").append(dimensions[1]).append("\t")
                .append("Storage type: ").append(getStorageType()).append("\n");
        return string.toString();
    }
}
