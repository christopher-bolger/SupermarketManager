package main.java.supermarketmanager.controller;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import main.java.supermarketmanager.model.linkedlist.LinkedList;
import main.java.supermarketmanager.model.supermarket.*;

import java.io.*;
import java.util.List;

public class SupermarketManager extends MarketStructure<Floor> {
    File file;
    public SupermarketManager(String name, int[] dimensions){
        super(name, dimensions);
        file = new File(name);
    }

    @Override
    public String objectDetails() {
        return "";
    }

    public boolean checkFloorName(String name){
        if(super.getList().isEmpty())
            return true;

        for(Floor floor : getList())
            if(floor.getName().equalsIgnoreCase(name))
                return false;
        return true;
    }

    //lots of raw usage here, I asked AI and to be honest I could try and be type safe by putting this into
    //marketManager and making sure the E extends marketmanager, but then I wouldn't be able to put GoodItem into shelf
    // so since its just me using this I know im not going to try and measure the dimensions of a good item
    //maybe i'll think of another way but this will do for now
    public boolean checkDimensions(MarketStructure parentItem ,int[] dimensionsToCheck){
        int[] dimensions = parentItem.getDimensions();
        if(dimensions.length != 2 || dimensionsToCheck.length != 2)
            return false;
        if(parentItem.isEmpty()) {
            return dimensions[0] >= dimensionsToCheck[0] && dimensions[1] >= dimensionsToCheck[1];
        }else{
            int totalArea = dimensions[0] * dimensions[1];
            for(Object obj : parentItem.getList()) {
                MarketStructure child = (MarketStructure) obj;
                totalArea -= child.getDimensions()[0] * child.getDimensions()[1];
                if(totalArea < 0)
                    return false;
            }
            boolean canFit = totalArea >= dimensionsToCheck[0]*dimensionsToCheck[1];
            boolean isWithinDimensions = dimensions[0] >= dimensionsToCheck[0] && dimensions[1] >= dimensionsToCheck[1];
            return canFit && isWithinDimensions;
        }
    }

    public boolean checkAisleName(String name) {
        if(getList().isEmpty())
            return true;
        for(Floor floor : getList())
            if(!floor.isEmpty())
                for(Aisle aisle : floor.getList())
                    if(aisle.getName().trim().equalsIgnoreCase(name.trim()))
                        return false;
        return true;
    }

    public boolean checkIndex(MarketStructure parent, int index){
        return parent.checkIndex(index);
    }

//    public String showAllListInfo(String type, List<?> items){
//        StringBuilder string = new StringBuilder();
//        string.append("Floors: ").append("\n");
//        if(super.getList().isEmpty()) {
//            string.append("No floors!");
//            return string.toString();
//        }
//
//        int index = 0;
//        for(Floor floor : super.getList()) {
//            string.append("Index: ").append(index).append(":")
//                    .append(" Level: ").append(floor.getFloor())
//                    .append("\tName: ").append(floor.getName());
//            index++;
//        }
//        return string.toString();
//    }
//
//    public String showAllAisles

    public String getListDetails(){
        if(getList().isEmpty())
            return "";
        return "";
    }
    public String toString(){
        if(getList().isEmpty())
            return "";

        int index = 0;
        int floorLevel, lastFloorLevel = -Integer.MAX_VALUE;
        StringBuilder string = new StringBuilder();
        for(Floor floor : getList()){
            floorLevel = floor.getFloor();
            if(floorLevel != lastFloorLevel)
                string.append(floor.objectDetails());
            string.append("Index: ").append(index).append("\t");
            string.append(floor);
            lastFloorLevel = floorLevel;
            index++;
        }
        return string.toString();
    }

    public void save() throws Exception {
        var xstream = new XStream(new DomDriver());
        ObjectOutputStream os = xstream.createObjectOutputStream(new FileWriter(file));
        os.writeObject(this);
        os.close();
    }

    public void load(String name) throws Exception {
        //list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[]{ SupermarketManager.class, Floor.class, Aisle.class, Shelf.class, GoodItem.class};

        //setting up the xstream object with default security and the above classes
        XStream xstream = new XStream(new DomDriver());
        //XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        //doing the actual serialisation to an XML file
        ObjectInputStream in = xstream.createObjectInputStream(new FileReader(name + ".xml"));
        SupermarketManager loaded = (SupermarketManager) in.readObject();
        in.close();
        super.setName(loaded.getName());
        super.setDimensions(loaded.getDimensions());
        super.setList(loaded.getList());
    }
}
