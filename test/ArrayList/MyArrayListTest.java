package ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import static org.junit.Assert.*;

public class MyArrayListTest {
    private List<String> l;

    @Before
    public void setUp() throws Exception {
        l = new MyArrayList<String>();
    }

    @After
    public void tearDown() throws Exception {
        l = null;
    }

    @Test
    public void sizeIsZero() {
        assertEquals(0, l.size());
    }

    @Test
    public void sizeIsOne() {
        l.add("One");
        assertEquals(1, l.size());
    }

    @Test
    public void isEmpty() {
        assertTrue(l.isEmpty());
    }

    @Test
    public void isNotEmpty() {
        l.add("One");
        assertFalse(l.isEmpty());
    }

    @Test
    public void containsElement() {
        l.add("One");
        assertTrue(l.contains("One"));
    }

    @Test
    public void notContainElement() {
        assertFalse(l.contains("One"));
    }

    @Test
    public void toArray() {
        String[] arr = {"One", "Two", "Three"};
        l.addAll(Arrays.asList(arr));
        assertArrayEquals(arr, l.toArray());
    }

    @Test
    public void toGivenSmallSizeArray() {
        String[] arr = {"One", "Two", "Three"};
        l.addAll(Arrays.asList(arr));
        assertArrayEquals(arr, l.toArray(new String[1]));
    }

    @Test
    public void toGivenEqualSizeArray() {
        String[] arr = {"One", "Two", "Three"};
        l.addAll(Arrays.asList(arr));
        assertArrayEquals(arr, l.toArray(new String[3]));
    }

    @Test
    public void toGivenBigSizeArray() {
        String[] arr = {"One", "Two", "Three"};
        String[] arr2 = {"Four", "Three", "Two", "One"};
        String[] arr3 = {"One", "Two", "Three", null};
        l.addAll(Arrays.asList(arr));
        assertArrayEquals(arr3, l.toArray(arr2));
    }

    @Test
    public void add() {
        assertTrue(l.add("One"));
        assertEquals("One", l.get(0));
    }

    @Test
    public void addToIndex() {
        String[] arr = {"One", "Three", "Four"};
        String[] arr2 = {"One", "Two", "Three", "Four"};
        l.addAll(Arrays.asList(arr));
        l.add(1, "Two");
        assertArrayEquals(l.toArray(), arr2);
    }

    @Test
    public void removeFromIndex() {
        l.add("One");
        assertEquals("One", l.remove(0));
    }

    @Test
    public void removeObj() {
        l.add("One");
        assertTrue(l.remove("One"));
    }

    @Test
    public void removeNotContain() {
        l.add("One");
        assertFalse(l.remove("Two"));
    }

    @Test
    public void addAll() {
        String[] arr = {"One", "Three", "Four"};
        l.addAll(Arrays.asList(arr));
        assertArrayEquals(arr, l.toArray());
    }

    @Test
    public void addAllFromIndex() {
        String[] arr = {"One", "Four"};
        String[] arr2 = {"Two", "Three"};
        String[] arr3 = {"One", "Two", "Three", "Four"};
        l.addAll(Arrays.asList(arr));
        l.addAll(1, Arrays.asList(arr2));
        assertArrayEquals(arr3, l.toArray());
    }

    @Test
    public void clear() {
        String[] arr3 = {"One", "Two", "Three", "Four"};
        l.addAll(Arrays.asList(arr3));
        assertEquals(4, l.size());
        l.clear();
        assertTrue(l.isEmpty());
        assertEquals(0, l.size());
    }

    @Test
    public void get() {
        String[] arr3 = {"One", "Two", "Three", "Four"};
        l.addAll(Arrays.asList(arr3));
        assertEquals("Four", l.get(l.size() - 1));
    }

    @Test
    public void set() {
        String[] arr3 = {"Two", "Two", "Three", "Four"};
        l.addAll(Arrays.asList(arr3));
        l.set(0, "One");
        assertEquals("One", l.get(0));
    }

    @Test
    public void indexOf() {
        String[] arr3 = {"One", "Two", "Three", "Four"};
        l.addAll(Arrays.asList(arr3));
        assertEquals(2, l.indexOf("Three"));
    }

    @Test
    public void indexOfNotContain() {
        String[] arr3 = {"One", "Two", "Three", "Four"};
        l.addAll(Arrays.asList(arr3));
        assertEquals(-1, l.indexOf("Five"));
    }

    @Test
    public void lastIndexOf() {
        String[] arr3 = {"One", "Two", "Three", "One"};
        l.addAll(Arrays.asList(arr3));
        assertEquals(3, l.lastIndexOf("One"));
    }

    @Test
    public void lastIndexOfNotContain() {
        String[] arr3 = {"One", "Two", "Three", "One"};
        l.addAll(Arrays.asList(arr3));
        assertEquals(-1, l.lastIndexOf("Four"));
    }

    @Test
    public void subList() {
        String[] arr3 = {"One", "Two", "Three", "Four"};
        String[] arr2 = {"One", "Two"};
        l.addAll(Arrays.asList(arr3));
        assertArrayEquals(arr2, l.subList(0, 2).toArray());
    }

    @Test
    public void retainAll() {
        String[] arr3 = {"One", "Two", "Three", "Four", "Five"};
        String[] arr2 = {"One", "Two"};
        l.addAll(Arrays.asList(arr3));
        l.retainAll(Arrays.asList(arr2));
        assertArrayEquals(arr2, l.toArray());
    }

    @Test
    public void removeAll() {
        String[] arr3 = {"One", "Two", "Three", "Four"};
        String[] arr2 = {"Three", "Four"};
        String[] arr1 = {"One", "Two"};
        l.addAll(Arrays.asList(arr3));
        l.removeAll(Arrays.asList(arr2));
        assertArrayEquals(arr1, l.toArray());
    }

    @Test
    public void containsAll() {
        String[] arr3 = {"One", "Two", "Three", "Four"};
        String[] arr2 = {"One", "Two"};
        l.addAll(Arrays.asList(arr3));
        assertTrue(l.containsAll(Arrays.asList(arr2)));
    }

    @Test
    public void notContainAll() {
        String[] arr3 = {"One", "Two", "Three", "Four"};
        String[] arr2 = {"One", "Five"};
        l.addAll(Arrays.asList(arr3));
        assertFalse(l.containsAll(Arrays.asList(arr2)));
    }

    @Test
    public void concurrentAdd() {
        String[] arr3 = {"One", "Three", "Five", "Seven"};
        String[] arr2 = {"Two", "Four", "Six", "Eight"};
        String[] arr1 = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight"};
        l.addAll(Arrays.asList(arr3));
        ListIterator<String> itr = l.listIterator();
        int i = 0;
        while (itr.hasNext()) {
            itr.next();
            itr.add(arr2[i++]);
        }

        assertArrayEquals(arr1, l.toArray());
    }
}