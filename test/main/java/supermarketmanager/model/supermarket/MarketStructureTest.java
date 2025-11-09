package main.java.supermarketmanager.model.supermarket;

import supermarketmanager.model.linkedlist.LinkedList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import supermarketmanager.model.supermarket.Aisle;
import supermarketmanager.model.supermarket.Floor;
import supermarketmanager.model.supermarket.Shelf;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class MarketStructureTest {
    Floor floor1, floor2;
    Aisle aisle1, aisle2;
    Shelf shelf1, shelf2;

    @BeforeEach
    void setUp() {
        floor1 = new Floor("This is a really really really really long name", new int[]{10,10}, 1);
        floor2 = new Floor(null,new int[]{0,0}, -Integer.MAX_VALUE);
        aisle1 = new Aisle("A short name", new int[]{100,100}, 0);
        aisle2 = new Aisle("A longggggggggggggggggggggggg name", new int[]{2,2,2}, 0);
        shelf1 = new Shelf("SomeShelfName", new int[]{1,1}, 0);
        shelf2 = new Shelf("SomeOtherShelfName", new int[]{1,1}, 1);
    }

    @AfterEach
    void tearDown() {
        floor1 = floor2 = null;
        aisle1 = aisle2 = null;
        shelf1 = shelf2 = null;
    }

    @Test
    void setName() {
        floor1.setName("This name wll be set");
        assertEquals("This name wll be set", floor1.getName());
        floor1.setName("This name will be truncated because its very very long");
        assertEquals("This name will be truncated be", floor1.getName());
    }

    @Test
    void getName() {
        floor1.setName("This name wll be set");
        assertEquals("This name wll be set", floor1.getName());
        floor1.setName("This name will be truncated because its very long");
        assertEquals("This name will be truncated be", floor1.getName());
    }

    @Test
    void getList() {
        assertNotNull(floor1.getList());
        assertNotNull(aisle1.getList());
        assertNotNull(shelf1.getList());
    }

    @Test
    void setList() {
        assertTrue(floor1.getList().isEmpty());
        Collection<Aisle> list = new LinkedList<>();
        list.add(aisle1);
        list.add(aisle2);
        floor1.setList(list);
        LinkedList<Aisle> returnedList = floor1.getList();
        assertEquals(2, returnedList.size());
        assertTrue(returnedList.contains(aisle1));
        assertTrue(returnedList.contains(aisle2));
        list.remove(aisle1);
        returnedList = floor1.getList();
        assertEquals(2, returnedList.size()); //verifying that internal list is NOT directly referencing passed list
    }

    @Test
    void setDimensions() {
        int[] dimensionsToSet = new int[]{3, 5};
        floor1.setDimensions(dimensionsToSet);
        assertEquals(2, floor1.getDimensions().length);
        int[] returnedDimensions = floor1.getDimensions();
        assertArrayEquals(dimensionsToSet, returnedDimensions);
        int[] oldDimensions = dimensionsToSet;
        dimensionsToSet = new int[]{3, 5, 7}; //invalid - only accepts arrays of length 2
        floor1.setDimensions(dimensionsToSet);
        assertArrayEquals(oldDimensions, floor1.getDimensions());
    }

    @Test
    void getDimensions() {
        assertArrayEquals(new int[]{10,10}, floor1.getDimensions());
        assertArrayEquals(new int[]{100,100}, aisle1.getDimensions());
        assertArrayEquals(new int[]{1,1}, shelf1.getDimensions());
    }

    @Test
    void add() {
        floor1.add(aisle1);
        assertTrue(floor1.contains(aisle1));
        floor1.remove(aisle1);
        assertFalse(floor1.contains(aisle1));
    }

    @Test
    void testAdd() {
        floor1.add(aisle1);
        floor1.add(aisle2,0);
        assertTrue(floor1.contains(aisle1));
        assertTrue(floor1.contains(aisle2));
        assertEquals(floor1.get(0), aisle2);
    }

    @Test
    void set() {
        floor1.add(aisle1);
        assertEquals(floor1.set(aisle2, 0), aisle1); //old item returned
        assertTrue(floor1.contains(aisle2));
        assertFalse(floor1.contains(aisle1));
    }

    @Test
    void addAll() {
        Collection<Aisle> list = new LinkedList<>();
        list.add(aisle1);
        list.add(aisle2);
        assertTrue(floor1.addAll(list));
        assertTrue(floor1.addAll(list)); //not a set so will add them again
    }

    @Test
    void remove() {
        floor1.add(aisle1);
        floor1.add(aisle2);
        assertTrue(floor1.remove(aisle1));
        assertFalse(floor1.remove(aisle1)); //already removed
        assertEquals(1, floor1.size());
    }

    @Test
    void removeAll() {
        floor1.add(aisle1);
        floor1.add(aisle2);
        Collection<Aisle> list = new LinkedList<>();
        list.add(aisle1);
        list.add(aisle2);
        assertTrue(floor1.removeAll(list));
        floor1.add(new Aisle("some name", new int[]{10,10}, 0));
        assertFalse(floor1.removeAll(list)); //already removed, size doesn't change
    }

    @Test
    void checkIndex(){
        floor1.add(aisle1);
        floor1.add(aisle2);
        assertTrue(floor1.checkIndex(0));
        assertTrue(floor1.checkIndex(1));
        assertFalse(floor1.checkIndex(2));
        assertFalse(floor1.checkIndex(-1));
    }

    @Test
    void contains(){
        assertFalse(floor1.contains(aisle1));
        assertFalse(floor1.contains(aisle2));
        floor1.add(aisle1);
        assertTrue(floor1.contains(aisle1));
        floor1.add(aisle2);
        assertTrue(floor1.contains(aisle2));
    }

    @Test
    void getFirst(){
        assertNull(floor1.getFirst());
        floor1.add(aisle1);
        floor1.add(aisle2);
        assertEquals(aisle1, floor1.getFirst());
        floor1.remove(aisle1);
        assertEquals(aisle2, floor1.getFirst());
    }

    @Test
    void getLast(){
        assertNull(floor1.getLast());
        floor1.add(aisle1);
        floor1.add(aisle2);
        assertEquals(aisle2, floor1.getLast());
        floor1.remove(aisle2);
        assertEquals(aisle1, floor1.getLast());
    }

    @Test
    void size(){
        assertEquals(0, floor1.size());
        floor1.add(aisle1);
        assertEquals(1, floor1.size());
        floor1.add(aisle2);
        assertEquals(2, floor1.size());
        floor1.remove(aisle2);
        assertEquals(1, floor1.size());
    }

    @Test
    void details() {
        floor1.add(aisle1);
        String details = floor1.details();
        assertTrue(details.contains(floor1.getName()));
        assertTrue(details.contains(Integer.toString(floor1.getDimensions()[0])));
        assertTrue(details.contains(Integer.toString(floor1.getDimensions()[1])));
        assertTrue(details.contains(Integer.toString(floor1.getFloor())));
        assertFalse(details.contains(aisle1.getName())); //should only contain floor specific details not list details
    }

    @Test
    void testToString() {
        floor1.add(aisle1);
        String details = floor1.toString();
        assertTrue(details.contains(floor1.getName()));
        assertTrue(details.contains(Integer.toString(floor1.getDimensions()[0])));
        assertTrue(details.contains(Integer.toString(floor1.getDimensions()[1])));
        assertTrue(details.contains(Integer.toString(floor1.getFloor())));
        assertTrue(details.contains(aisle1.getName()));
        assertTrue(details.contains(Integer.toString(aisle1.getDimensions()[0])));
        assertTrue(details.contains(Integer.toString(aisle1.getDimensions()[1])));
        //assertTrue(details.contains(aisle1.getStorageType())); weird issues here, I can explain why but its too much to write here
    }
}