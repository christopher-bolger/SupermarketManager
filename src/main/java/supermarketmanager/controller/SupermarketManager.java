package main.java.supermarketmanager.controller;

import main.java.supermarketmanager.model.linkedlist.LinkedList;
import main.java.supermarketmanager.model.supermarket.Aisle;
import main.java.supermarketmanager.model.supermarket.Floor;

public class SupermarketManager{
    String marketName;
    LinkedList<Floor> floors;

    public SupermarketManager(String name){
        floors = new LinkedList<>();
        if(name != null && !name.isEmpty())
            if(name.length() > 30)
                marketName = name.substring(0, 30);
            else
                marketName = name;
        else
            marketName = "Supermarket";
    }

    public void setName(String name){
        if(name != null && !name.isEmpty())
            if(name.length() > 30)
                marketName = name.substring(0, 30);
            else
                marketName = name;
    }

    public String getName(){
        return marketName;
    }

    public String toString(){
        if(floors.isEmpty())
            return "";

        int size = floors.size()-1, index = 0;
        StringBuilder string = new StringBuilder();
        for(Floor floor : floors){
            if(index == size)
                string.append(floor.toString());
            else
                string.append(floor.toString()).append("\n");
        }
        return string.toString();
    }

    public void load() {
    }

    public void save(){

    }
}
