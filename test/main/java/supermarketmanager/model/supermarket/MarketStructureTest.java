package main.java.supermarketmanager.model.supermarket;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarketStructureTest {
    Floor Floor1, Floor2;
    Aisle Aisle1, Aisle2;
    Shelf Shelf1, Shelf2;

    @BeforeEach
    void setUp() {
        Floor1 = new Floor("This is a really really really really long name", new int[]{10,10}, 1);
        Floor2 = new Floor(null,new int[]{0,0}, -Integer.MAX_VALUE);
        Aisle1 = new Aisle("A short name", new int[]{100,100});
        Aisle2 = new Aisle("A longggggggggggggggggggggggg name", new int[]{2,2,2});
        Shelf1 = new Shelf("SomeShelfName", new int[]{1,1}, 0);
        Shelf2 = new Shelf("SomeOtherShelfName", new int[]{1,1}, 1);
    }

    @AfterEach
    void tearDown() {
        Floor1 = Floor2 = null;
        Aisle1 = Aisle2 = null;
        Shelf1 = Shelf2 = null;
    }

    @Test
    void setName() {
    }

    @Test
    void getName() {
    }

    @Test
    void getList() {
    }

    @Test
    void setList() {
    }

    @Test
    void setDimensions() {
    }

    @Test
    void getDimensions() {
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