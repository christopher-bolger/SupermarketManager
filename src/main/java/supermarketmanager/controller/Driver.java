package main.java.supermarketmanager.controller;

import main.java.supermarketmanager.model.linkedlist.LinkedList;
import main.java.supermarketmanager.model.supermarket.Floor;
import main.java.supermarketmanager.model.supermarket.MarketStructure;
import main.java.supermarketmanager.utils.ScannerInput;

import java.util.List;

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
        int option = showMenu();
        while(option != 0) {
            switch (option) {
                case 1 -> save();
                case 2 -> load();
                case 3 -> addFloor();
                case 4 -> addAisle();
                //            case 5 -> addShelf();
                //            case 6 -> addGoodItem();
                case 7 -> showFloors();
                case 8 -> showFloorInformation(getIndex());
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
                8) Show Floor information
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
            allowed = manager.checkDimensions(manager, dimensions);
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
        manager.add(new Floor(name, dimensions, floorNumber));
    }

    public void addAisle(){
        System.out.println("Which floor would you like to add the Aisle too? Index: ");
    }

    public LinkedList<Floor> getFloors(){
        return manager.getList();
    }

    public int getIndex(){
        showFloors();
        return ScannerInput.readNextInt(-1, "Which index would you like to select?");
    }

    public void showFloors(){
        System.out.println(manager.toString());
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
                System.out.println(floor);
        }while(floor == null);
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