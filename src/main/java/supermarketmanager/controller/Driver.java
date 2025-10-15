package main.java.supermarketmanager.controller;

import main.java.supermarketmanager.model.supermarket.Floor;
import main.java.supermarketmanager.utils.ScannerInput;

public class Driver {
    private SupermarketManager manager;
    public static void main(String[] args) throws Exception {
        new Driver().start();
    }

    public void start() throws Exception {
        boolean load = loadData();
        if(!load) {
            String name = ScannerInput.readNextLine("Enter the name of your supermarket: ");
            int[] dimensions = new int[2];
            dimensions[0] = ScannerInput.readNextInt(0, "Enter x dimension of your market: ");
            dimensions[1] = ScannerInput.readNextInt(0, "Enter y dimension of your market: ");
            manager = new SupermarketManager(name, dimensions);
        }else{
            int[] dimensions = new int[2];
            dimensions[0] = 1;
            dimensions[1] = 1;
            manager = new SupermarketManager("Name", dimensions);
            load();
        }
        menu();
    }

    public void menu() throws Exception {
        switch(showMenu()){
            case 0 -> System.exit(0);
            case 1 -> save();
            case 2 -> load();
            case 3 -> addFloor();
            case 4 -> addAisle();
            case 5 -> addShelf();
            case 6 -> addGoodItem();
        }
    }

    public int showMenu(){
        return ScannerInput.readNextInt("""
                Welcome to Supermarket Manager
                ------------------------------
                0) Exit
                1) Save
                2) Load
                ------------------------------
                3) Add a Floor Area
                4) Add an Aisle
                5) Add a Shelf
                6) Add a GoodItem
                ------------------------------
                """);
    }

    public void addFloor(){
        String name = ScannerInput.readNextLine("Enter the name of your Floor Area: ");
        int[] dimensions = new int[2];
        dimensions[0] = ScannerInput.readNextInt(0, "Enter the x dimension: ");
        dimensions[1] = ScannerInput.readNextInt(0,"Enter the y dimension: ");
        int floorNumber = ScannerInput.readNextInt("Enter the floor level: ");
        manager.add(new Floor(name, dimensions, floorNumber));
    }

    public boolean loadData() {
        char ans = ScannerInput.readNextChar("Load data? Y/N:");
        return ans == 'y' || ans == 'Y';
    }

    public void load() throws Exception {
        manager.load();
    }

    public void save() throws Exception {
        manager.save();
    }
}