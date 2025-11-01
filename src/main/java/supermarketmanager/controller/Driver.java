package main.java.supermarketmanager.controller;

import main.java.supermarketmanager.model.linkedlist.LinkedList;
import main.java.supermarketmanager.model.supermarket.*;
import main.java.supermarketmanager.utils.ScannerInput;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

//TODO
// switch arrays under storageType and weightType to linkedLists
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
                case 6 -> addGoodItem();
                case 7 -> autoAddGoodItem();
                case 8 -> showFloors();
                case 9 -> showFloorInformation(getIndex(manager));
                case 10 -> showAisleInformation();
                case 11 -> showShelfInformation();
                case 12 -> searchFloor();
                case 13 -> searchAisle();
                case 14 -> searchShelf();
                case 15 -> searchGoodItem();
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
                7) Auto-add a GoodItem
                ------------------------------
                8) Show all Floors
                9) Show Floor Aisles
                10) Show Aisle Shelves
                11) Show Shelf GoodItems
                ------------------------------
                12) Search for Floor Area
                13) Search for Aisle
                14) Search for Shelf
                15) Search for GoodItem
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
        showFloorInformation(index);
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

    public GoodItem generateGoodItem(){
        String goodItemName = ScannerInput.readNextLine("Enter the name of your GoodItem: ");
        String description = ScannerInput.readNextLine("Enter the description of your GoodItem: ");
        String photoURL = ScannerInput.readNextLine("Enter the photo URL of your good item: ");
        double price = ScannerInput.readNextDouble("Enter the price of your GoodItem: ");
        double weight = ScannerInput.readNextDouble("Enter the weight of your GoodItem: ");
        int weightIndex = ScannerInput.readNextInt("Enter the index of the weight category - " + Arrays.toString(GoodItem.weightTypes) + ":");
        int quantity = ScannerInput.readNextInt("Enter the quantity of your GoodItem: ");
        return new GoodItem(goodItemName, description, price, quantity, weight, weightIndex, 0, photoURL);
    }

    public void addGoodItem(){
        Floor floor = (Floor) manager.get(getIndex(manager));
        Aisle aisle = (Aisle) floor.get(getIndex(floor));
        Shelf shelf = (Shelf) aisle.get(getIndex(aisle));
        GoodItem newItem = generateGoodItem();
        newItem.setStorageType(aisle.getStorageTypeIndex());
        boolean added = manager.addObject(newItem, shelf);
        if(added)
            System.out.println("Successfully added the GoodItem!");
        else
            System.out.println("Failed to add the GoodItem!");
    }

    public void autoAddGoodItem(){
        GoodItem newItem = generateGoodItem();
        newItem.setStorageType(ScannerInput.readNextInt("Enter the index of the storage type: " + Arrays.toString(GoodItem.storageTypes)));
        LinkedList<Object> foundItem = (LinkedList<Object>) manager.find(newItem);
        if(foundItem != null && !foundItem.isEmpty())
            manager.addObject(newItem, manager.findParent(foundItem.getFirst()));
        Shelf shelf = manager.findSuitableLocation(newItem);
        if(shelf == null)
            System.out.println("No suitable locations found!");
        else{
            Aisle aisle = (Aisle) manager.findParent(shelf);
            boolean added = shelf.add(newItem);
            if(added) {
                System.out.println("Successfully added " + newItem.getName() + " to shelf " + shelf.getName() + " in aisle " + aisle.getName() + ".");
            }else
                System.out.println("Failed to add the GoodItem!");
        }
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
        System.out.println(manager.details() + "\n" + manager.getListDetails());
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

    public void showShelfInformation(){
        Floor floor = (Floor) manager.get(getIndex(manager));
        Aisle aisle = (Aisle) floor.get(getIndex(floor));
        Shelf shelf = (Shelf) aisle.get(getIndex(floor));
        System.out.println(shelf.details() + "\n" + shelf.getListDetails());
    }

    public void search(String type, String... property){
        if(Objects.equals(type, "floor") || Objects.equals(type, "aisle") || Objects.equals(type, "shelf") || Objects.equals(type, "goodItem")){
            String nameToFind = property[0], description = "";
            if(Objects.equals(type, "goodItem"))
                description = property[1];
            Object toSearch;
            switch(type){
                case "aisle" -> toSearch = new Aisle(nameToFind);
                case "shelf" -> toSearch = new Shelf(nameToFind);
                case "goodItem" -> toSearch = new GoodItem(nameToFind, description);
                default -> toSearch = new Floor(nameToFind);
            }
            LinkedList<Object> list = (LinkedList<Object>) manager.find(toSearch);
            if(list == null || list.isEmpty())
                System.out.println("No " + type + "s found with that property!");
            else if (list.size() == 1)
                System.out.println(list.getFirst());
            else {
                System.out.println("Multiple " + type + "s found with that property!");
                System.out.println(list);
            }
        }
    }

    public void searchFloor(){
        if(manager.isEmpty())
            System.out.println("No Floors, add one first!");
        else {
            String property = ScannerInput.readNextLine("Enter the name (or part of the name) of the floor you're looking for: ");
            search("floor", property);
        }
    }

    public void searchAisle(){
        if(manager.isEmpty()) {
            System.out.println("No Floors, add one first!");
            return;
        }
        String property = ScannerInput.readNextLine("Enter the name (or part of the name) of the aisle you're looking for: ");
        search("aisle", property);
    }

    public void searchShelf(){
        if(manager.isEmpty()) {
            System.out.println("No Floors, add one first!");
            return;
        }
        String property = ScannerInput.readNextLine("Enter the name (or part of the name) of the shelf you're looking for: ");
        search("shelf", property);
    }

    public void searchGoodItem(){
        if(manager.isEmpty()) {
            System.out.println("No Floors, add one first!");
            return;
        }
        String property = ScannerInput.readNextLine("Enter the name (or part of the name) of the product you're looking for: ");
        String description = ScannerInput.readNextLine("Enter the description (or part of) of the product you are looking for: ");
        search("goodItem", property, description);
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