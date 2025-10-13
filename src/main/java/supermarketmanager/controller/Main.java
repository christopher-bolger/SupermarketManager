package main.java.supermarketmanager.controller;

import main.java.supermarketmanager.model.linkedlist.LinkedList;

public class Main {
    public static void main(String[] args) {
        LinkedList<String> test = new LinkedList<>();
        test.add("A");
        test.add("B");
        test.add("C");
        for(String s : test) {
            System.out.println(s);
        }
        boolean removed = test.remove("B");
        System.out.println(removed);
        for(String s : test) {
            System.out.println(s);
        }
    }
}