package main.java.supermarketmanager.controller;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import main.java.supermarketmanager.model.linkedlist.LinkedList;
import main.java.supermarketmanager.model.supermarket.*;

import java.io.*;
import java.util.Collection;

public class SupermarketManager extends MarketStructure<Floor> {
    File file;
    public SupermarketManager(String name, int[] dimensions, File file){
        super(name, dimensions);
        this.file = file;
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

    public boolean checkFloorName(String name){
        if(super.getList().isEmpty())
            return true;

        for(Floor floor : getList())
            if(floor.getName().equalsIgnoreCase(name))
                return false;
        return true;
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

    public boolean addObject(Object item, MarketStructure<?> parentItem){
        if(item == null || parentItem == null)
            return false;
        return switch(item){
            case Floor floor -> addFloor(floor);
            case Aisle aisle -> addAisle(aisle, (Floor) parentItem);
            case Shelf shelf -> addShelf(shelf, (Aisle) parentItem);
            case GoodItem goodItem -> addGoodItem(goodItem, (Shelf) parentItem);
            default -> false;
        };
    }

    public boolean addObject(Floor item){
        return addFloor(item);
    }

    private boolean addFloor(Floor item){
        return getList().add(item);
    }

    private boolean addAisle(Aisle item, Floor parentFloor){
        boolean accepted = checkAisleName(item.getName());
        if(accepted)
            return parentFloor.add(item);
        return false;
    }

    private boolean addShelf(Shelf item, Aisle parentAisle){
        return parentAisle.add(item);
    }

    private boolean addGoodItem(GoodItem item, Shelf parentShelf){
        LinkedList<Object> foundItems = (LinkedList<Object>) find((Object) item);
        if(foundItems != null && foundItems.isEmpty())
            return parentShelf.add(item);
        else if(foundItems == null)
            return false;
        else if(foundItems.size() == 1)
            return parentShelf.replace((GoodItem) foundItems.getFirst(), item);
        else
            return false; //should never be more than one of the same type
    }

    //this is kinda unsightly but really not that complex
    public Collection<Object> find(Object original){
        if(original == null)
            return null;
        Collection<Object> list = new LinkedList<>();
        switch(original){
            case Floor floor : {
                for(Floor f : getList()){
                    if(f.toString().contains(floor.getName()))
                        list.add(f);
                }
                break;
            }
            case Aisle aisle : {
                for(Floor f : getList()){
                    for(Aisle a : f.getList()){
                        if(a.toString().contains(aisle.getName()))
                            list.add(a);
                    }
                }
                break;
            }
            case Shelf shelf : {
                for(Floor f : getList()){
                    for(Aisle a : f.getList()){
                        for(Shelf s : a.getList()){
                            if(s.toString().contains(shelf.getName()))
                                list.add(s);
                        }
                    }
                }
                break;
            }
            case GoodItem goodItem : {
                for(Floor f : getList()){
                    for(Aisle a : f.getList()){
                        for(Shelf s : a.getList()){
                            for(GoodItem g : s.getList()){
                                if(g.toString().contains(goodItem.getName()) && g.toString().contains(goodItem.getDescription()))
                                    list.add(g);
                            }
                        }
                    }
                }
                break;
            }
            default: break;
        }
        if(list.isEmpty())
            return null;
        return list;
    }

    public boolean checkIndex(MarketStructure<Object> parent, int index){
        return parent.checkIndex(index);
    }

    public void setFile(File file){
        this.file = file;
    }

    public File getFile(){
        return file;
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

    public void save() throws Exception {
        var xstream = new XStream(new DomDriver());
        ObjectOutputStream os = xstream.createObjectOutputStream(new FileWriter(file + ".xml"));
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
        setFile(loaded.getFile());
        super.setDimensions(loaded.getDimensions());
        super.setList(loaded.getList());
    }

    @Override
    public boolean replace(Floor itemToReplace, Floor item) {
        if(list.contains(itemToReplace))
            return list.set(list.indexOf(itemToReplace), item) == itemToReplace;
        return false;
    }

    @Override
    public String details() {
        StringBuilder string = new StringBuilder();
        int[] dimensions = getDimensions();
        string.append("Name: ").append(super.getName()).append("\t")
                .append("Size: ").append(dimensions[0]).append(", ").append(dimensions[1]).append("\n")
                .append("File name: ").append(file.toString()).append("\n");
        return string.toString();
    }
}
