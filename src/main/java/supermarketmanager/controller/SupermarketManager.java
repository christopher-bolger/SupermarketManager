package main.java.supermarketmanager.controller;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import main.java.supermarketmanager.model.linkedlist.LinkedList;
import main.java.supermarketmanager.model.supermarket.Floor;
import main.java.supermarketmanager.model.supermarket.MarketStructure;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class SupermarketManager extends MarketStructure<Floor> {
    public SupermarketManager(String name, int[] dimensions){
        super(name, dimensions);
    }

    @Override
    public String objectDetails() {
        return "";
    }

    public String toString(){
        if(getList().isEmpty())
            return "";

        int size = getList().size()-1, index = 0;
        StringBuilder string = new StringBuilder();
        for(Floor floor : getList()){
            if(index == size)
                string.append(floor.toString());
            else
                string.append(floor.toString()).append("\n");
        }
        return string.toString();
    }

    public void save() throws Exception {
        var xstream = new XStream(new DomDriver());
        ObjectOutputStream os = xstream.createObjectOutputStream(new FileWriter("market.xml"));
        os.writeObject(this);
        os.close();
    }


    public void load() throws Exception {
        //list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[]{ SupermarketManager.class};

        //setting up the xstream object with default security and the above classes
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        //doing the actual serialisation to an XML file
        ObjectInputStream in = xstream.createObjectInputStream(new FileReader("market.xml"));
        setList((LinkedList<Floor>) in.readObject());
        in.close();
    }
}
