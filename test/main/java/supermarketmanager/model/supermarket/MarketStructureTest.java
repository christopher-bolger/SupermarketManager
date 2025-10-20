package main.java.supermarketmanager.model.supermarket;

import main.java.supermarketmanager.model.linkedlist.LinkedList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    }

    @Test
    void testAdd() {
    }

    @Test
    void set() {
    }

    @Test
    void addAll() {
    }

    @Test
    void remove() {
    }

    @Test
    void removeAll() {
    }

    @Test
    void objectDetails() {
    }

    @Test
    void testToString() {
    }
}