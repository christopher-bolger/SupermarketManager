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
                //            case 4 -> addAisle();
                //            case 5 -> addShelf();
                //            case 6 -> addGoodItem();
                case 7 -> showFloorInformation(getIndex());
            }
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

    public void addFloor(){
        showFloors();

        boolean allowed = false;
        String name = "";
        do {
            name = ScannerInput.readNextLine("Enter the name of your Floor Area: ");
            if(manager.checkFloorName(name))
                allowed = true;
            if(!allowed)
                System.out.println("Invalid Floor Name! Please try again!");
        }while(!allowed);

        int[] dimensions = new int[2];
        dimensions[0] = ScannerInput.readNextInt(0, "Enter the x dimension: ");
        dimensions[1] = ScannerInput.readNextInt(0,"Enter the y dimension: ");
        int floorNumber = ScannerInput.readNextInt("Enter the floor level: ");
        manager.add(new Floor(name, dimensions, floorNumber));
    }

    public LinkedList<Floor> getFloors(){
        return manager.getList();
    }

    public int getIndex(){
        showFloors();
        return ScannerInput.readNextInt(-1, "Which index would you like to select?");
    }

    public void showFloors(){
        List<Floor> floors = getFloors();
        if(floors.isEmpty())
            return;
        System.out.println("Floors: ");
        int index = 0;
        for(Floor floor : floors){
            System.out.println("Index: " + index + ":" + " Level: " + floor.getFloor() + "\tName: " + floor.getName());
            index++;
        }
    }

    public void showFloorInformation(int index){
        List<Floor> floors = getFloors();
        if(floors.isEmpty()) {
            System.out.println("No Floors, add one first!");
            return;
        }
        Floor floor = floors.get(index);
        if(floor == null)
            System.out.println("Invalid index");
        else
            System.out.println(floors.get(index).toString());
    }

    public boolean loadData() {
        char ans = ScannerInput.readNextChar("Load data? Y/N:");
        return ans == 'y' || ans == 'Y';
    }

    public void load() throws Exception {
        manager.load();
        menu();
    }

    public void save() throws Exception {
        manager.save();
        menu();
    }
}