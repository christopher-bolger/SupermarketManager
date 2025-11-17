package supermarketmanager.model.supermarket;

public class Shelf extends MarketStructure<GoodItem>{
    int shelfNumber;

    public Shelf(String name, int[] dimensions, int shelfNumber) {
        super(name, dimensions);
        if(shelfNumber >= 0)
            this.shelfNumber = shelfNumber;
        else
            this.shelfNumber = -1;
    }

    public Shelf(String name){
        super(name, new int[] {1,1});
        shelfNumber = 0;
    }

    public int getShelfNumber() {
        return shelfNumber;
    }

    public void setShelfNumber(int shelfNumber) {
        if(shelfNumber <= 0)
            this.shelfNumber = 0;
    }

    @Override
    public boolean replace(GoodItem itemToReplace, GoodItem item) {
        if(list.contains(itemToReplace)) {
            item.setQuantity(itemToReplace.getQuantity() + item.getQuantity());
            return list.set(list.indexOf(itemToReplace), item) == itemToReplace;
        }
        return false;
    }

    @Override
    public double totalValue() {
        double totalValue = 0;
        if(list.isEmpty())
            return totalValue;
        for(GoodItem item : list) {
            totalValue += item.totalValue();
        }
        return totalValue;
    }

    @Override
    public String details() {
        StringBuilder string = new StringBuilder();
        int[] dimensions = getDimensions();
        string.append("Shelf: ").append(super.getName()).append(" \t")
                .append("Size: ").append(dimensions[0]).append(", ").append(dimensions[1]).append(" \t")
                .append("Total Value: ").append(totalValue()).append(" \t")
                .append("Shelf number: ").append(shelfNumber);
        return string.toString();
    }
}
