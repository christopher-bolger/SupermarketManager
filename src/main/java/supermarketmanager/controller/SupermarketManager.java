package supermarketmanager.controller;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import supermarketmanager.model.linkedlist.LinkedList;
import supermarketmanager.model.supermarket.*;

import java.io.*;
import java.sql.SQLOutput;
import java.util.Collection;
import java.util.Objects;

public class SupermarketManager extends MarketStructure<Floor> {
    File file;
    public SupermarketManager(String name, int[] dimensions, File file){
        super(name, dimensions);
        this.file = file;
    }

    public boolean checkDimensions(MarketStructure<?> parentItem ,int[] dimensionsToCheck){
        int[] dimensions = parentItem.getDimensions();
        if(dimensions.length != 2 || dimensionsToCheck.length != 2)
            return false;
        if(parentItem.isEmpty()) {
            return dimensions[0] >= dimensionsToCheck[0] && dimensions[1] >= dimensionsToCheck[1];
        }else{
            int totalArea = dimensions[0] * dimensions[1];
            for(Object obj : parentItem.getList()) {
                MarketStructure<?> child = (MarketStructure<?>) obj;
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

    public boolean addObject(MarketStructure<?> item, MarketStructure<?> parentItem){
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

    private boolean addFloor(Floor item){
        return getList().add(item);
    }

    public boolean addAisle(Aisle item, Floor parentFloor){
        boolean accepted = checkAisleName(item.getName());
        if(accepted)
            return parentFloor.add(item);
        return false;
    }

    @Override
    public double totalValue(){
        double totalValue = 0;
        if(list.isEmpty())
            return totalValue;
        for(Floor floor : list){
            totalValue += floor.totalValue();
        }
        return totalValue;
    }

    public boolean addShelf(Shelf item, Aisle parentAisle){
        return parentAisle.add(item);
    }

    private boolean addGoodItem(GoodItem item, Shelf parentShelf){
        for(GoodItem g : getAllGoodItems()){
            if(g.equals(item)) {
                ((Shelf) findParent(g)).replace(g, item);
                return true;
            }
        }
        return parentShelf.add(item);
    }

    //I really don't like this
    public MarketStructure<?> findParent(Object child){
        if(child instanceof Floor || child instanceof Aisle || child instanceof Shelf || child instanceof GoodItem)
            switch(child){
                case Floor floor : {
                    if(!isEmpty() && contains(floor))
                        return this;
                    break;
                }
                case Aisle aisle : {
                    for(Floor floor : getList())
                        if(!floor.isEmpty())
                            if(floor.contains(aisle))
                                return floor;
                    break;
                }
                case Shelf shelf : {
                    for(Floor floor : getList())
                        if(!floor.isEmpty())
                            for(Aisle aisle : floor.getList())
                                if(!aisle.isEmpty())
                                    if(aisle.contains(shelf))
                                        return aisle;
                    break;
                }
                case GoodItem goodItem : {
                    for(Floor floor : getList())
                        if(!floor.isEmpty())
                            for(Aisle aisle : floor.getList())
                                if(!aisle.isEmpty())
                                    for(Shelf s : aisle.getList())
                                        if(!s.isEmpty())
                                            if(s.contains(goodItem))
                                                return s;
                    break;
                }
                default : break;
            }
        return null;
    }

    //this is kinda unsightly but really not that complex
    //TODO
    // need to check if the list is empty before going into the next for loop
    // surely there's a better way because that's a lot of indentation.
    public Collection<? extends MarketStructure<?>> find(Object toFind){
        if(toFind == null || isEmpty())
            return null;
        Collection<MarketStructure<?>> list;
        list = new LinkedList<>();
        switch(toFind){
            case Floor floor : {
                for(Floor f : getList())
                    if(f.equals(floor))
                        list.add(f);
                break;
            }
            case Aisle aisle : {
                for(Floor f : getList())
                    if (!f.isEmpty())
                        for (Aisle a : f.getList())
                            if (a.equals(aisle))
                                list.add(a);
                break;
            }
            case Shelf shelf : {
                for(Floor f : getList())
                    if(!f.isEmpty())
                        for(Aisle a : f.getList())
                            if(!a.isEmpty())
                                for (Shelf s : a.getList())
                                    if (s.equals(shelf))
                                        list.add(s);
                break;
            }
            case GoodItem goodItem : {
                for(Floor f : getList())
                    if(!f.isEmpty())
                        for(Aisle a : f.getList())
                            if(!a.isEmpty())
                                for(Shelf s : a.getList())
                                    if(!s.isEmpty())
                                        for (GoodItem g : s.getList())
                                            if (g.roughEquals(goodItem)) //can't do equals because it compares name & weight, we search for name & description
                                                list.add(g);
                break;
            }
            default: break;
        }
        if(list.isEmpty())
            return null;
        return list;
    }

    public Collection<GoodItem> getAllGoodItems(){
        if(isEmpty())
            return null;
        Collection<GoodItem> returnList = new LinkedList<>();
        for(Floor f : getList())
            if(!f.isEmpty())
                for(Aisle a : f.getList())
                    if(!a.isEmpty())
                        for(Shelf s : a.getList())
                            if(!s.isEmpty())
                                returnList.addAll(s.getList());
        return returnList;
    }

    public Collection<Shelf> getAllShelves(){
        if(isEmpty())
            return null;
        Collection<Shelf> returnList = new LinkedList<>();
        for(Floor f : getList())
            if(!f.isEmpty())
                for(Aisle a : f.getList())
                    if(!a.isEmpty())
                        returnList.addAll(a.getList());
        return returnList;
    }

    public Collection<Aisle> getAllAisles(){
        if(isEmpty())
            return null;
        Collection<Aisle> returnList = new LinkedList<>();
        for(Floor f : getList())
            if(!f.isEmpty())
                returnList.addAll(f.getList());
        return returnList;
    }

    public Shelf findSuitableLocation(GoodItem toFind){
        if(toFind == null || isEmpty())
            return null;
        LinkedList<GoodItem> otherItems = (LinkedList<GoodItem>) getAllGoodItems();
        LinkedList<Aisle> otherAisles = (LinkedList<Aisle>) getAllAisles();
        LinkedList<Shelf> shelves = null;
        String[] splitName = toFind.getName().toLowerCase().split(" ");
        String[] splitDescription = toFind.getDescription().toLowerCase().split(" ");
        int[] score = new int[0], shelfScore = new int[0];

        if(otherAisles != null && !otherAisles.isEmpty()){
            shelves = new LinkedList<>();
            if(otherAisles.isEmpty())
                return null;
            for(Aisle aisle : otherAisles)
                if(!aisle.getList().isEmpty())
                    if(aisle.getStorageType().equals(toFind.getStorageType()))
                        shelves.addAll(aisle.getList());
            shelfScore = new int[shelves.size()];
            for(int i = 0; i < shelves.size(); i++) {
                for (String s : splitName)
                    if (shelves.get(i).getName().toLowerCase().contains(s.toLowerCase()) || findParent(shelves.get(i)).getName().toLowerCase().contains(s.toLowerCase()))
                        shelfScore[i]++;
                for(String s : splitDescription)
                    if(shelves.get(i).getName().toLowerCase().contains(s.toLowerCase()) || findParent(shelves.get(i)).getName().toLowerCase().contains(s.toLowerCase()))
                        shelfScore[i]++;
            }
        }
        if(otherItems != null && !otherItems.isEmpty()) { //looking for similar good items
            score = new int[otherItems.size()];
            for (int i = 0; i < otherItems.size(); i++) {
                if (Objects.equals(toFind.getStorageType(), otherItems.get(i).getStorageType())) {
                    String[] nameToCompare = otherItems.get(i).getName().toLowerCase().split(" ");
                    String[] descriptionToCompare = otherItems.get(i).getDescription().toLowerCase().split(" ");
                    for (String value : nameToCompare)
                        for (String s : splitName)
                            if (value.equals(s))
                                score[i]++;
                    for (String s : descriptionToCompare)
                        for (String string : splitDescription)
                            if (s.equals(string))
                                score[i]++;
                }
            }
        }

        if(score.length == 0 && shelfScore.length == 0)
            return null;
        int highestScore = -1;
        int highestIndex = 0;
        int highestList = 0;
        for(int i = 0; i < score.length; i++)
            if(score[i] > highestScore) {
                highestScore = score[i];
                highestIndex = i;
            }
        for(int i = 0; i < shelfScore.length; i++)
            if(shelfScore[i] > highestScore) {
                highestScore = shelfScore[i];
                highestIndex = i;
                highestList = 1;
            }
        if(highestScore < 1)
            return null;
        if(highestList == 0){
            return (Shelf) findParent(otherItems.get(highestIndex));
        }else{
            return shelves.get(highestIndex);
        }
    }

    public Collection<MarketStructure<?>> search(String[] searchItems){
        if(isEmpty() || searchItems.length == 0)
            return null;
        Collection<MarketStructure<?>> found = new LinkedList<>();
        String name = "", description = "";
        if(!searchItems[0].isEmpty()) {
            name = searchItems[0];
            System.out.println("Searching for " + name);
            found.addAll(find(new Floor(name)));
            found.addAll(find(new Aisle(name)));
            found.addAll(find(new Shelf(name)));
        }
        if(!searchItems[1].isEmpty()) {
            description = searchItems[1];
            found.addAll(find(new GoodItem(name, description)));
        }
        return found;
    }

    public boolean checkIndex(MarketStructure<?> parent, int index){
        return parent.checkIndex(index);
    }

    public void setFile(File file){
        this.file = file;
    }

    public File getFile(){
        return file;
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
                .append("File: ").append(file.toString()).append("\n");
        return string.toString();
    }

    public void save() throws Exception {
        var xstream = new XStream(new DomDriver());
        ObjectOutputStream os = xstream.createObjectOutputStream(new FileWriter(file));
        os.writeObject(this);
        os.close();
    }

    public void load(File file) throws Exception {
        //list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[]{ SupermarketManager.class, Floor.class, Aisle.class, Shelf.class, GoodItem.class};

        //setting up the xstream object with default security and the above classes
        XStream xstream = new XStream(new DomDriver());
        //XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        //doing the actual serialisation to an XML file
        ObjectInputStream in = xstream.createObjectInputStream(new FileReader(file));
        SupermarketManager loaded = (SupermarketManager) in.readObject();
        in.close();
        super.setName(loaded.getName());
        setFile(loaded.getFile());
        super.setDimensions(loaded.getDimensions());
        super.setList(loaded.getList());
    }
}
