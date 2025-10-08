package main.java.supermarketmanager.controller;

import main.java.supermarketmanager.model.LinkedList;

public class Main {
    public static void main(String[] args) {
        LinkedList<String> test = new LinkedList<>();
        test.add("A");
        test.add("B");
        test.add("C");
        LinkedList<String> test2 = new LinkedList<>();
        test2.add("D");
        test2.add("E");
        test2.add("F");
        for(String s : test) {
            System.out.println(s);
            System.out.println("\n");
        }
        test.addAll(1 ,test2);
        for(String s : test) {
            System.out.println(s);
        }
    }
}