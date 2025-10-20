package main.java.supermarketmanager.controller;

import main.java.supermarketmanager.model.linkedlist.LinkedList;
import main.java.supermarketmanager.model.supermarket.Aisle;
import main.java.supermarketmanager.model.supermarket.Floor;
import main.java.supermarketmanager.model.supermarket.MarketStructure;
import main.java.supermarketmanager.model.supermarket.Shelf;
import main.java.supermarketmanager.utils.ScannerInput;

import java.io.File;
import java.util.Arrays;

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
            File file = new File(ScannerInput.readNextLine("Enter the name of the file for saving: "));
            manager = new SupermarketManager(name, dimensions, file);
        }else{
            int[] dimensions = new int[2];
            dimensions[0] = 1;
            dimensions[1] = 1;
            manager = new SupermarketManager("Name", dimensions, new File("LOADING-SAVING IS WRONG"));
            load();
        }
        menu();
    }

    public void menu() throws Exception {
        int option = showMenu();
        while(option != 0) {
            switch (option) {
                case 1 -> save();
                case 2 -> load();
                case 3 -> addFloor();
                case 4 -> addAisle();
                case 5 -> addShelf();
                //            case 6 -> addGoodItem();
                case 7 -> showFloors();
                case 8 -> showFloorInformation(getIndex(manager));
                case 9 -> showAisleInformation();
            }
            ScannerInput.readNextLine("Press enter to continue....");
            option = showMenu();
        }
        System.exit(0);
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
                7) Show all Floors
                8) Show Floor Aisles
                9) Show Aisle Shelves
                ------------------------------
                """);
    }

    public String getName(){
        boolean allowed;
        String name;
        do {
            name = ScannerInput.readNextLine("Enter the name of your Floor Area: ");
            allowed = manager.checkFloorName(name);
            if(!allowed)
                System.out.println("Invalid Floor Name! Please try again!");
        }while(!allowed);
        return name;
    }

    public int[] getDimensions(MarketStructure itemToCheckAgainst){
        boolean allowed;
        int[] dimensions = new int[2];
        do {
            dimensions[0] = ScannerInput.readNextInt(0, "Enter the x dimension: ");
            dimensions[1] = ScannerInput.readNextInt(0, "Enter the y dimension: ");
            allowed = manager.checkDimensions(itemToCheckAgainst, dimensions);
            if(!allowed)
                System.out.println("Invalid Dimension! Please try again!");
        }while(!allowed);
        return dimensions;
    }

    public void addFloor(){
        showFloors();
        String name = getName();
        int[] dimensions = getDimensions(manager);
        int floorNumber = ScannerInput.readNextInt("Enter the floor level: ");
        boolean added = manager.add(new Floor(name, dimensions, floorNumber));
        if(added)
            System.out.println("Floor Added!");
        else
            System.out.println("Floor Not Added!");
    }

    public void addAisle(){
        System.out.println("Which floor would you like to add the Aisle too? Index: ");
        int index = getIndex(manager);
        Floor floorToUpdate = (Floor) manager.get(index);
        String name = getAisleName();
        int[] dimensions = getDimensions(floorToUpdate);
        int storageType = ScannerInput.readNextInt(-1,"Enter the storage type (Index):" + "\n" + Arrays.toString(Aisle.storageTypes));
        boolean added = floorToUpdate.add(new Aisle(name, dimensions, storageType));
        if(added)
            System.out.println("Successfully added the Aisle!");
        else
            System.out.println("Failed to add the Aisle!");
    }

    public LinkedList<Floor> getFloors(){
        return manager.getList();
    }

    public String getAisleName(){
        boolean valid;
        String name;
        do{
            name = ScannerInput.readNextLine("Enter the name of your Aisle (must be unique): ");
            valid = manager.checkAisleName(name);
            if(!valid)
                System.out.println("Invalid Aisle name! Please try again!");
        }while(!valid);
        return name;
    }

    public void addShelf(){
        Floor floor = (Floor) manager.get(getIndex(manager));
        Aisle aisle = (Aisle) floor.get(getIndex(floor));
        String shelfName = ScannerInput.readNextLine("Enter the name of your Shelf: ");
        int[] dimensions = getDimensions(floor);
        int shelfNumber = ScannerInput.readNextInt(0, "Enter the shelf number: ");
        boolean added = aisle.add(new Shelf(shelfName, dimensions, shelfNumber));
        if(added)
            System.out.println("Successfully added the Shelf!");
        else
            System.out.println("Failed to add the Shelf!");
    }

    public int getIndex(MarketStructure itemToCheckIndexIn){
        System.out.println(itemToCheckIndexIn.getListDetails());
        int index;
        boolean allowed;
        do{
            index = ScannerInput.readNextInt(-1, "Which index would you like to select?");
            allowed = itemToCheckIndexIn.checkIndex(index);
            if(!allowed)
                System.out.println("Invalid Index! Please try again!");
        }while(!itemToCheckIndexIn.checkIndex(index));
        return index;
    }

    public void showFloors(){
        System.out.println(manager.getListDetails());
    }

    public void showFloorInformation(int index){
        if(manager.getList().isEmpty()) {
            System.out.println("No Floors, add one first!");
            return;
        }
        Floor floor = null;
        do {
            floor = (Floor) manager.get(index);
            if (floor == null)
                System.out.println("Invalid index, please try again!");
            else
                System.out.println(floor.details() + "\n" + floor.getListDetails());
        }while(floor == null);
    }

    public void showAisleInformation(){
        Floor floor = (Floor) manager.get(getIndex(manager));
        Aisle aisle = (Aisle) floor.get(getIndex(floor));
        System.out.println(aisle.details() + "\n" + aisle.getListDetails());
    }

    public boolean loadData() {
        char ans = ScannerInput.readNextChar("Load data? Y/N:");
        return ans == 'y' || ans == 'Y';
    }

    public void load() throws Exception {
        manager.load(ScannerInput.readNextLine("File name: "));
        menu();
    }

    public void save() throws Exception {
        manager.save();
        menu();
    }
}