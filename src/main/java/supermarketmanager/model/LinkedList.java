package main.java.supermarketmanager.model;

import java.io.InvalidClassException;
import java.util.InvalidPropertiesFormatException;

public class LinkedList<E> {
    public LinkedList<E> next;
    private E element;

    public LinkedList() {
        next = null;
        element = null;
    }

    private LinkedList(E element, LinkedList<E> next) throws Exception{
        if(element != null){
            this.element = element;
            this.next = next;
        }else{
            throw new Exception("Something went wrong");
        }
    }

    // Add element/node methods
    public boolean add(E content) {
        if(this.element != null || content == null)
            return false;

        LinkedList<E> temp = this;
        while(temp.next != null){
            temp = temp.next;
        }
        return temp.addNode() && temp.next.addElement(content);

//        if(this.element == null){
//            return setElement(content);
//        }else if(next != null){
//            return next.add(element);
//        }else{
//            return addNode() && next.add(content);
//        }
        //My assumption is that there's less overhead without a while loop
        //But the recursion would probably be slower
    }

    public boolean insert(int index, E element) {
        if ((index < 0 || index > size()) && element != null)
            return false;

        int count = 0;
        LinkedList<E> temp = this;
        while(count != index-1) {
            temp = temp.next;
            count++;
        }

        LinkedList<E> nextLink = temp.next;
        try {
            temp.next = new LinkedList<E>(element, nextLink);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    private boolean addElement(E element) {
        if(this.element == null) {
            this.element = element;
            return true;
        }
        return false;
    }

    private boolean addNode(){
        if(next == null) {
            next = new LinkedList<E>();
            return true;
        }
        return false;
    }

    //getter methods
    //TODO
    // What if an element is passed that does not exist within the list?
    public E get(E element){
        LinkedList<E> temp = this;
        boolean found = false;
        while(temp.next != null) {
            if (temp.getElement().equals(element)){
                found = true;
                break;
                }
            temp = temp.next;
        }
        return found ? temp.getElement() : null;
    }

    private E getElement() {
        return element;
    }

    //misc methods
    public int size(){
        return next == null ? 0 : next.size()+1;
    }

    public String toString(){
        return element == null ? "" : element.toString() + "\n" + next.toString();
    }
}
