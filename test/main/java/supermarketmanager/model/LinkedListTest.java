package main.java.supermarketmanager.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListTest {
    LinkedList<String> list;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        list = new LinkedList<>();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        list = null;
    }

    @Test
    void testSize() {
        assertTrue(list.add("HI"));
        assertEquals(1, list.size());
        assertTrue(list.add("HIIII"));
        assertEquals(2, list.size());
        assertFalse(list.add(null));
        assertEquals(2, list.size());
    }

    @Test
    void testAdd(){
        assertTrue(list.add("HI"));
        assertTrue(list.add("HIIII"));
        assertTrue(list.add("Howdy"));
        assertTrue(list.insert(1, "position 1"));
        assertEquals(4, list.size());
        System.out.println(list);
    }
}