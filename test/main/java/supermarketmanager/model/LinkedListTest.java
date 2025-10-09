package main.java.supermarketmanager.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListTest {
    LinkedList<String> emptyList, listWithElements, listWithOneElement;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        emptyList = new LinkedList<>();

        listWithElements = new LinkedList<>();
        listWithElements.add("Hi");
        listWithElements.add("Howdy");
        listWithElements.add("Hello");
        listWithElements.add("Morning");

        listWithOneElement = new LinkedList<>();
        listWithOneElement.add("Single");
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
    void testSet(){
        assertNull(emptyList.set(0, "HII"));
        assertEquals("Evening", listWithElements.set(3, "Evening"));
        assertNull(listWithElements.get("Morning")); // the object that was replaced
    }

    @Test
    void testIndexOf(){
        assertEquals(-1, emptyList.indexOf("Something"));
        assertEquals(0, listWithOneElement.indexOf("Single"));
        assertEquals(3, listWithElements.indexOf("Morning"));
    }

    @Test
    void testLastIndexOf(){
        assertEquals(-1, emptyList.lastIndexOf("Something"));
        assertEquals(0, listWithOneElement.lastIndexOf("Single"));
        assertEquals(3, listWithElements.lastIndexOf("Morning"));
        listWithElements.add("Morning");
        assertEquals(4, listWithElements.lastIndexOf("Morning"));
    }

    @Test
    void testSubList(){
        List<String> testList = emptyList.subList(0,0);
        assertEquals(0, testList.size());
    }

    @Test
    void testReversedOrder(){
        LinkedList<String> reversed = (LinkedList<String>) listWithElements.reversed();
        assertEquals(reversed.getFirst(), listWithElements.getLast());
        assertEquals(reversed.getLast(), listWithElements.getFirst());
    }

//
//    @Test
//    void testAdd(){
//        assertTrue(emptyList.add("HI"));
//        assertTrue(emptyList.add("HIIII"));
//        assertTrue(emptyList.add("Howdy"));
//        assertFalse(emptyList.add(null));
//        assertEquals(3, emptyList.size());
//    }
//
//    @Test
//    void testInsert(){
//        assertTrue(listWithElements.insert(2, "position 2"));
//        assertTrue(listWithElements.insert(4, "position 4"));
//        assertFalse(listWithElements.insert(0, null));
//        assertFalse(listWithElements.insert(6, "not possible")); // 6 elements in array but it counts from 0
//        assertFalse(listWithElements.insert(-1, "not possible"));
//        assertFalse(emptyList.insert(0, "EmptyList, can't insert"));
//        emptyList.add("FirstElement");
//        assertTrue(emptyList.insert(0, "Now it should work"));
//    }
//
//    @Test
//    void testGet(){
//        assertEquals("Morning", listWithElements.get(listWithElements.size()-1));
//        assertEquals("Hi", listWithElements.get("Hi"));
//        assertEquals("Hi", listWithElements.get(0));
//        assertEquals("Single", listWithOneElement.getFirst());
//        assertEquals("Single", listWithOneElement.get(0));
//        assertEquals("Single", listWithOneElement.get("Single"));
//        assertNull(listWithOneElement.get(null));
//        assertNull(listWithElements.get("NotInTheList"));
//        assertNull(listWithElements.get(listWithElements.size()));
//        assertTrue(listWithElements.add("Good Morning"));
//        assertEquals("Good Morning", listWithElements.getLast());
//    }
//
//    @Test
//    void testGetFirst(){
//        assertNull(emptyList.getFirst());
//        assertEquals("Hi", listWithElements.getFirst());
//        listWithElements.insert(0, "iH");
//        assertEquals("iH", listWithElements.getFirst());
//        listWithElements.insert(0, "hmm");
//        //Making sure there's no data loss
//        assertEquals("hmm", listWithElements.getFirst());
//        assertEquals("iH", listWithElements.get(1));
//    }
//
//    @Test
//    void testGetLast(){
//        assertNull(emptyList.getLast());
//        assertEquals("Morning", listWithElements.getLast());
//        listWithElements.add("Evening");
//        assertEquals("Evening", listWithElements.getLast());
//    }
//
//    @Nested
//    class testRemoveByIndex {
//        @Test
//        void testEmptyList(){
//            assertFalse(emptyList.remove(0));
//        }
//
//        @Test
//        void testSingleItemList(){
//            assertTrue(listWithOneElement.remove(0));
//            assertFalse(listWithOneElement.remove(1));
//            assertFalse(listWithOneElement.remove(0));
//        }
//
//        @Test
//        void testMultiItemList(){
//            assertTrue(listWithElements.remove(1));
//            assertNull(listWithElements.get(3)); // is now size() 3 so index 3 doesn't exist
//            String check = listWithElements.toString();
//            assertFalse(check.contains("Howdy"));
//            assertTrue(listWithElements.remove(listWithElements.size()-1));
//            assertEquals(2, listWithElements.size());
//            check = listWithElements.toString();
//            assertFalse(check.contains("Morning"));
//            assertNull(listWithElements.get(3)); // "Morning" was removed, size is now 2;
//            assertTrue(listWithElements.remove(listWithElements.size()-1));
//            assertEquals(1, listWithElements.size());
//        }
//    }
//
//    @Nested
//    class testRemoveByElement{
//        @Test
//        void testEmptyList(){
//            assertFalse(emptyList.remove(null));
//            assertFalse(emptyList.remove("Nothing"));
//        }
//
//        @Test
//        void testSingleItemList(){
//            assertFalse(listWithOneElement.remove(null));
//            assertFalse(listWithOneElement.remove("Not in List"));
//            assertTrue(listWithOneElement.remove("Single"));
//            assertNull(listWithOneElement.get("Single"));
//        }
//
//        @Test
//        void testMultiItemList(){
//            assertFalse(listWithElements.remove(null));
//            assertEquals(4, listWithElements.size());
//
//            assertTrue(listWithElements.remove("Hi"));
//            assertFalse(listWithElements.toString().contains("Hi"));
//            assertEquals(3, listWithElements.size());
//            assertEquals("Howdy", listWithElements.getFirst());
//
//            assertFalse(listWithElements.remove("Not in List"));
//            assertNotNull(listWithElements.get("Howdy"));
//            assertTrue(listWithElements.remove("Howdy"));
//            assertNull(listWithElements.get("Howdy"));
//            assertEquals("Hello", listWithElements.getFirst());
//        }
//    }
//
//    @Test
//    void testClear(){
//        assertTrue(listWithOneElement.clear());
//        assertTrue(emptyList.clear());
//        assertTrue(listWithOneElement.clear());
//    }
//
//    @Test
//    void testToString(){
//        String string = emptyList.toString();
//        assertNull(string);
//        string = listWithOneElement.toString();
//        assertTrue(string.contains("Single"));
//        string = listWithElements.toString();
//        assertTrue(string.contains("Hi"));
//        assertTrue(string.contains("Howdy"));
//        assertTrue(string.contains("Hello"));
//        assertTrue(string.contains("Morning"));
//        listWithOneElement.remove("Single");
//        string = listWithOneElement.toString();
//        assertNull(string);
//        listWithOneElement.add("Single");
//        string = listWithOneElement.toString();
//        assertNotNull(string);
//    }
}