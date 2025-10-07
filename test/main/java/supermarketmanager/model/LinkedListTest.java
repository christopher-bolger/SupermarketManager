package main.java.supermarketmanager.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListTest {
    LinkedList<String> emptyList, listWithElements;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        emptyList = new LinkedList<>();
        listWithElements = new LinkedList<>();
        listWithElements.add("Hi");
        listWithElements.add("Howdy");
        listWithElements.add("Hello");
        listWithElements.add("Morning");
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        emptyList = listWithElements = null;
    }

    @Test
    void testSize() {
        assertEquals(0, emptyList.size());
        assertTrue(emptyList.add("HI"));
        assertEquals(1, emptyList.size());
        assertTrue(emptyList.add("HIIII"));
        assertEquals(2, emptyList.size());
        assertFalse(emptyList.add(null));
        assertEquals(2, emptyList.size());
    }

    @Test
    void testAdd(){
        assertTrue(emptyList.add("HI"));
        assertTrue(emptyList.add("HIIII"));
        assertTrue(emptyList.add("Howdy"));
        assertFalse(emptyList.add(null));
        assertEquals(3, emptyList.size());
    }

    @Test
    void testInsert(){
        assertTrue(listWithElements.insert(2, "position 2"));
        assertTrue(listWithElements.insert(4, "position 4"));
        assertFalse(listWithElements.insert(6, "not possible")); // 6 elements in array but it counts from 0
        assertFalse(listWithElements.insert(-1, "not possible"));
        assertFalse(emptyList.insert(0, "EmptyList, can't insert"));
        emptyList.add("FirstElement");
        assertTrue(emptyList.insert(0, "Now it should work"));
    }

    @Test
    void testGet(){
        assertEquals("Morning", listWithElements.get(listWithElements.size()-1));
        assertEquals("Hi", listWithElements.get("Hi"));
        assertEquals("Hi", listWithElements.get(0));
        assertNull(listWithElements.get("NotInTheList"));
        assertNull(listWithElements.get(listWithElements.size()));
    }

    @Test
    void testGetFirst(){
        assertNull(emptyList.getFirst());
        assertEquals("Hi", listWithElements.getFirst());
        listWithElements.insert(0, "iH");
        assertEquals("iH", listWithElements.getFirst());
        listWithElements.insert(0, "hmm");
        //Making sure there's no data loss
        assertEquals("hmm", listWithElements.getFirst());
        assertEquals("iH", listWithElements.get(1));
    }

    @Test
    void testGetLast(){
        assertNull(emptyList.getLast());
        assertEquals("Morning", listWithElements.getLast());
        listWithElements.add("Evening");
        assertEquals("Evening", listWithElements.getLast());
    }

    @Test
    void testRemoveByIndex(){
        assertFalse(emptyList.remove(0)); //empty list
        assertTrue(listWithElements.remove(0));
        assertNull(listWithElements.get(3)); // list is of size() 3
        assertFalse(listWithElements.remove(100)); //outside range
        String check = listWithElements.toString();
        assertFalse(check.contains("Hi"));
        assertTrue(listWithElements.remove(listWithElements.size()-1));
        check = listWithElements.toString();
        assertFalse(check.contains("Morning"));
        listWithElements.add("Good Morning");
        assertEquals("Good Morning", listWithElements.get(listWithElements.size() - 1));
        assertEquals(3, listWithElements.size());
        assertNull(listWithElements.get(4));
    }

    @Test
    void testRemoveByElement(){
        assertFalse(emptyList.remove("Nothing")); //empty list
        assertTrue(listWithElements.remove("Hi"));
        assertEquals(3, listWithElements.size());
        assertNull(listWithElements.get(4)); // list is of size() 3
        assertFalse(listWithElements.remove("Not in List")); //outside range
        String check = listWithElements.toString();
        assertFalse(check.contains("Hi"));
        assertTrue(listWithElements.remove("Howdy"));
        check = listWithElements.toString();
        assertFalse(check.contains("Howdy"));
        listWithElements.add("Good Morning");
        assertEquals("Good Morning", listWithElements.get("Good Morning"));
        assertEquals(3, listWithElements.size());
        assertNull(listWithElements.get("Howdy"));
    }
}