package main.java.supermarketmanager.controller;

import main.java.supermarketmanager.utils.ScannerInput;

public class Driver {
    private SupermarketManager manager;
    public static void main(String[] args) {
        new Driver().start();
    }

    public void start() {
        boolean load = loadData();
        if(!load) {
            String name = ScannerInput.readNextLine("Enter the name of your supermarket: ");
            manager = new SupermarketManager(name);
        }else{
            loadFile();
        }
    }

    public boolean loadData() {
        char ans = ScannerInput.readNextChar("Load data? Y/N:");
        return ans == 'y' || ans == 'Y';
    }

    public void loadFile() {
        manager.load();
    }
}