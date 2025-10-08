package main.java.supermarketmanager.model;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Node<E>{
    private E content;
    public Node<E> next;

    public Node(){
        content = null;
        next = null;
    }

    public Node(E content, Node<E> next){
        if(next != null) {
            setContent(content);
            this.next = next;
        }
    }

    public boolean setContent(E content){
        if(content != null) {
            this.content = content;
            return true;
        }
        return false;
    }

    public E getContent(){
        return content;
    }

    public String toString(){
        return content.toString();
    }
}