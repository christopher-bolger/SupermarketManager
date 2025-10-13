package main.java.supermarketmanager.model.linkedlist;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
        emptyList = listWithElements = listWithOneElement = null;
    }

    @Test
    void testToString(){
        String s = emptyList.toString();
        assertTrue(s.isEmpty()); //returns ""
        String s2 = listWithElements.toString();
        for(String a : listWithElements)
            assertTrue(s2.contains(a));
    }

    @Test
    void testContainsAll(){
        LinkedList<String> list = new LinkedList<>();
        list.addAll(listWithElements);
        assertTrue(list.containsAll(listWithElements));

        list.clear();
        list.addAll(listWithOneElement);
        assertTrue(list.containsAll(listWithOneElement));
        list.add("Not in the list");
        assertTrue(list.containsAll(listWithOneElement));
        assertFalse(listWithOneElement.containsAll(list));

        list.clear();
        list.addAll(listWithElements);
        assertTrue(list.containsAll(listWithElements));
        assertTrue(listWithElements.containsAll(list));
        list.add("Not in the list");
        assertTrue(list.containsAll(listWithElements));
        assertFalse(listWithOneElement.containsAll(list));
    }

    @Test
    void testIsEmpty(){
        assertTrue(emptyList.isEmpty());
        assertFalse(listWithElements.isEmpty());
        assertFalse(listWithOneElement.isEmpty());
        assertTrue(new LinkedList<String>().isEmpty());
    }

    @Test
    void testContains(){
        assertFalse(emptyList.contains("Howdy"));
        assertFalse(listWithElements.contains(null));
        assertFalse(listWithElements.contains(listWithElements));
        assertTrue(listWithOneElement.contains("Single"));

        LinkedList<String> list = new LinkedList<>();
        list.addAll(listWithElements);
        for(String s : list)
            assertTrue(list.contains(s));
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
        assertEquals(4, listWithElements.size());
    }

    @Test
    void testSet(){
        assertNull(emptyList.set(0, "HII"));
        assertEquals("Evening", listWithElements.set(3, "Evening"));
        listWithOneElement.set(0, null); //will not be set
        assertNotNull(listWithOneElement.getFirst()); // should still be "Single"
        assertNull(listWithElements.get("Morning")); // the object that was replaced
    }

    @Test
    void testIndexOf(){
        assertEquals(-1, emptyList.indexOf("Something"));
        assertEquals(0, listWithOneElement.indexOf("Single"));
        assertEquals(3, listWithElements.indexOf("Morning"));
        assertEquals(-1, listWithOneElement.indexOf("Evening")); //not in the list
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
        List<String> testList = emptyList.subList(0,0);// valid range, but empty list
        assertEquals(0, testList.size());

        testList = listWithOneElement.subList(0,1); //valid range
        assertEquals(listWithOneElement.toString(), testList.toString()); //testing string because I think if I compare the objects it'll be false because a new list is returned

        testList = listWithOneElement.subList(1,2); // outside range, empty list returned
        assertTrue(testList.isEmpty());

        testList = listWithElements.subList(0, listWithElements.size()); //whole list
        assertEquals(listWithElements.toString(), testList.toString());

        testList = listWithElements.subList(1,2); // one item
        assertEquals(listWithElements.get(1), testList.toString());

        testList = listWithElements.subList(3,0); //impossible range
        assertTrue(testList.isEmpty()); //it's inefficient but adding another true/false to the if statement felt like too much
    }

    @Test
    void testReversedOrder(){
        LinkedList<String> reversed = (LinkedList<String>) listWithElements.reversed();
        assertEquals(reversed.getFirst(), listWithElements.getLast());
        assertEquals(reversed.getLast(), listWithElements.getFirst());

        reversed = (LinkedList<String>) listWithOneElement.reversed();
        assertEquals(reversed.getFirst(), listWithOneElement.getLast());
        assertEquals(reversed.getLast(), listWithOneElement.getFirst());

        reversed = (LinkedList<String>) emptyList.reversed();
        assertTrue(reversed.isEmpty());
        assertEquals(reversed.getFirst(), emptyList.getFirst());
    }

    @Nested
    class toArray {
        @Test
        void testToArray() {
            Object[] array = listWithElements.toArray();
            for (Object s : array)
                assertTrue(listWithElements.contains(s));
            array = emptyList.toArray();
            assertEquals(0, array.length); //empty list returns empty array
        }

        @Test
        void testToArrayConstructor(){
            String[] array = listWithElements.toArray(new String[0]);
            assertEquals(array.length, listWithElements.size());
            for(String s : array)
                assertTrue(listWithElements.contains(s));
            String[] array2 = new String[100];
            array2 = listWithElements.toArray(array2); //Should return array of same length, with extra indexes being set to null
            assertNotEquals(array2.length, listWithElements.size());
            for(String s : array2)
                if(s != null)
                    assertTrue(listWithElements.contains(s));
        }
    }

    @Nested
    class testAdd{
        @Test
        void testAddFirst(){
            emptyList.addFirst("Hi");
            assertEquals("Hi", listWithElements.getFirst());
            emptyList.addFirst("Howdy");
            assertEquals("Howdy", emptyList.getFirst());
            listWithElements.addFirst("First");
            assertEquals("First", listWithElements.getFirst());
        }

        @Test
        void testAddLast(){
            emptyList.addLast("Hi");
            assertEquals("Hi", emptyList.getLast());
            emptyList.addLast("Howdy");
            assertEquals("Howdy", emptyList.getLast());
            listWithElements.addLast("Last");
            assertEquals("Last", listWithElements.getLast());
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
        void testAddIndex(){
            emptyList.add(0,"HI"); // empty list, can't add if no index
            assertTrue(emptyList.isEmpty());
            listWithOneElement.add(1, "Second"); // can't add if the index doesn't already have something there
            assertEquals(1, listWithOneElement.size());
            listWithElements.add(1,"Fella");
            assertEquals(5, listWithElements.size()); //verifying addition to list, no loss in data
            assertEquals("Fella", listWithElements.get(1));
            assertEquals("Howdy", listWithElements.get(2)); // was in index 1, was pushed to index 2
            listWithElements.add(3, "Something");
            assertEquals("Something", listWithElements.get(3));
        }

        @Test
        void testAddAll(){
            LinkedList<String> list = new LinkedList<>();
            list.add("Something new");
            list.add(null); //won't be added - list will be of size() = 2
            list.add("Something else thats new");

            emptyList.addAll(null);
            assertTrue(emptyList.isEmpty());

            assertFalse(listWithOneElement.addAll(new LinkedList<>())); // returns false if the size hasn't changed

            assertTrue(emptyList.addAll(list)); //can add to an empty list
            assertEquals(2, emptyList.size());

            assertTrue(listWithOneElement.addAll(list));
            assertEquals(3, listWithOneElement.size());

            assertTrue(listWithOneElement.contains(list.getFirst()));
            assertTrue(listWithOneElement.contains(list.getLast()));

            assertTrue(listWithOneElement.addAll(list)); //can add the same thing twice
            assertEquals(5, listWithOneElement.size());
        }

        @Test
        void testAddAllIndex(){
            LinkedList<String> list = new LinkedList<>();
            list.add("Something new");
            list.add(null); //won't be added - list will be of size() = 2
            list.add("Something else thats new");

            assertFalse(emptyList.addAll(0,null)); //index is invalid so returns false
            assertTrue(emptyList.isEmpty());

            assertFalse(listWithOneElement.addAll(0, new LinkedList<>())); // returns false if the size hasn't changed

            assertFalse(emptyList.addAll(0, list)); //can add to an empty list
            assertTrue(emptyList.isEmpty());

            assertTrue(listWithOneElement.addAll(0,list)); //index must already exist in the list, can't addLast etc
            assertEquals(3, listWithOneElement.size());

            assertTrue(listWithOneElement.contains(list.getFirst()));
            assertTrue(listWithOneElement.contains(list.getLast()));

            assertTrue(listWithOneElement.addAll(2, list)); //can add the same thing twice
            assertEquals(5, listWithOneElement.size());
        }

        @Test
        void testAddAllToIndex(){

        }
    }

    @Nested
    class testGet{
        @Test
        void testGetElement(){
            assertNull(emptyList.get("hi"));
            assertNotNull(listWithElements.get("Hi"));
            assertNotNull(listWithOneElement.get("Single"));
            assertNull(listWithOneElement.get("Morning"));
        }

        @Test
        void testGetElementIndex(){
            assertNull(emptyList.get(0));
            assertNotNull(listWithElements.get(0));
            assertNotNull(listWithOneElement.get(0));
            assertNull(listWithOneElement.get(1));
            assertNull(listWithElements.get(listWithElements.size()));
        }

        @Test
        void testGetFirst(){
            assertNull(emptyList.getFirst());
            assertNotNull(listWithElements.getFirst());
            assertNotNull(listWithOneElement.getFirst());
        }

        @Test
        void testGetLast(){
            assertNull(emptyList.getLast());
            assertEquals(listWithOneElement.getFirst(), listWithOneElement.getLast());
            assertNotNull(listWithElements.getLast());
        }
    }

    @Nested
    class testRemove{
        @Test
        void testRemoveLast(){
            assertNull(emptyList.removeLast());
            assertNotNull(listWithOneElement.removeLast());
            assertEquals(0, listWithOneElement.size());
            assertNotNull(listWithElements.removeLast());
            assertEquals(3, listWithElements.size());
            assertFalse(listWithElements.contains("Morning"));
        }

        @Test
        void testRemoveFirst(){
            assertNull(emptyList.removeFirst());
            assertEquals(listWithElements.getFirst(), listWithElements.removeFirst());
            assertEquals(listWithOneElement.getFirst(), listWithOneElement.removeFirst());
            assertTrue(listWithOneElement.isEmpty());
        }

        @Test
        void testRemoveElement(){
            assertFalse(listWithElements.remove(null));
            assertFalse(emptyList.remove("Hi")); // doesn't exist
            assertTrue(listWithOneElement.remove("Single"));
            assertTrue(listWithOneElement.isEmpty()); // verifying removal
            assertTrue(listWithElements.remove(listWithElements.getFirst())); // "Hi"
            assertTrue(listWithElements.remove(listWithElements.getLast())); // "Morning"
            assertFalse(listWithElements.remove("Not in list")); //invalid call - doesn't exist in the list
            assertEquals(2, listWithElements.size()); // was 4, removed 2 = 2
            String whatsLeft = listWithElements.toString();
            assertTrue(whatsLeft.contains("Howdy"));
            assertTrue(whatsLeft.contains("Hello"));
            assertFalse(whatsLeft.contains("Morning"));
            assertFalse(whatsLeft.contains("Hi"));
        }

        @Test
        void testRemoveIndex(){
            assertNull(emptyList.remove(0)); //empty list; should return null
            assertEquals(listWithOneElement.getFirst(), listWithOneElement.remove(0));
            assertTrue(listWithOneElement.isEmpty());
            assertEquals(listWithElements.getLast(), listWithElements.remove(listWithElements.size()-1));
            assertFalse(listWithElements.toString().contains("Morning")); //veryifying removal
            assertEquals(3, listWithElements.size());
            assertNull(listWithElements.remove(listWithElements.size())); //invalid index
        }

        @Test
        void testRetainAll(){
            LinkedList<String> list = new LinkedList<>();
            list.add("Hi");
            list.add("Hello");

            assertFalse(emptyList.retainAll(new LinkedList<>())); //only returns false of the main list is empty

            assertTrue(listWithElements.retainAll(list));
            assertTrue(listWithElements.containsAll(list));
            assertFalse(listWithElements.contains("Morning")); // removed elements
            assertFalse(listWithElements.contains("Howdy"));

            assertTrue(listWithOneElement.retainAll(list)); // if no elements are in common, then the list will be cleared
            assertTrue(listWithOneElement.isEmpty());
        }

        @Test
        void testRemoveAll(){
            LinkedList<String> list = new LinkedList<>();
            list.add("Hi");
            list.add("Hello");

            assertFalse(emptyList.removeAll(new LinkedList<>()));

            //TODO
            // do this for retainAll()?
            assertFalse(listWithElements.removeAll(list)); //returns false if the size of the list doesn't change
            assertFalse(listWithElements.containsAll(list));
            assertTrue(listWithElements.contains("Morning")); // removed elements
            assertTrue(listWithElements.contains("Howdy"));

            LinkedList<String> listToCompare = new LinkedList<>();
            for(String string : listWithElements)
                listToCompare.add(string);
            list.clear();
            list.add("Something");
            list.add("Not in the list");
            assertFalse(listWithElements.removeAll(list));
            assertEquals(listToCompare.toString(), listWithElements.toString()); //list should remain unchanged
        }
    }
}