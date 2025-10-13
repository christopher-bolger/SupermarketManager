package main.java.supermarketmanager.model.supermarket;

import main.java.supermarketmanager.model.linkedlist.LinkedList;

import java.util.Collection;

public abstract class MarketStructure {
    private String name = "";
    private LinkedList<? extends MarketStructure> list = new LinkedList<>();

    public MarketStructure(String name) {
        if(name != null && !name.isEmpty())
            if(name.length() > 30)
                this.name = name.substring(0, 30);
            else
                this.name = name;
    }

    public void setName(String name){
        if(name != null && !name.isEmpty())
            if(name.length() > 31)
                this.name = name.substring(0, 31);
            else
                this.name = name;
    }

    public String getName(){
        return name;
    }

    public LinkedList<? extends MarketStructure> getList() {
        return list;
    }

    public void setList(Collection<MarketStructure> list) {
        if(list != null)
            this.list = (LinkedList<MarketStructure>) list;
    }

    public boolean addItem(MarketStructure item){
        return list.add(item);
    }

    public void addItem(MarketStructure item, int index){
        list.add(index, item);
    }

    public boolean addAll(Collection<? extends MarketStructure> items){
        return list.addAll(items);
    }

    public boolean removeItem(MarketStructure item){
        return list.remove(item);
    }

    public boolean removeAll(Collection<? extends MarketStructure> item){
        return list.removeAll(item);
    }
}
