import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CircularLinkedListTestStudent {
    private static final int TIMEOUT = 200;
    private CircularDoublyLinkedList<Integer> list;

    @Before
    public void setup() {
        list = new CircularDoublyLinkedList<>();
    }

    @Test(timeout = TIMEOUT)
    public void testAdd() {
        list.addAtIndex(0, 84);
        list.addAtIndex(1, 23);
        list.addAtIndex(1, 83);
        list.addAtIndex(0, 96);

        assertEquals(4, list.size());

        LinkedListNode<Integer> node = list.getHead();
        assertNotNull(node);
        assertNotNull(node.getPrevious());
        assertEquals((Integer) 96, node.getData());
        node = node.getNext();
        assertNotNull(node);
        assertNotNull(node.getPrevious());
        assertEquals((Integer) 84, node.getData());
        node = node.getNext();
        assertNotNull(node);
        assertNotNull(node.getPrevious());
        assertEquals((Integer) 83, node.getData());
        node = node.getNext();
        assertNotNull(node);
        assertNotNull(node.getPrevious());
        assertEquals((Integer) 23, node.getData());
        assertNotNull(node.getNext());
    }

    @Test(timeout = TIMEOUT)
    public void testRemove() {
        list.addAtIndex(0, 84);
        list.addAtIndex(1, 23);
        list.addAtIndex(1, 83);
        list.addAtIndex(0, 96);
        list.addAtIndex(3, 58);

        assertEquals(5, list.size());

        list.removeAtIndex(3);

        assertEquals(4, list.size());

        LinkedListNode<Integer> node = list.getHead();
        assertNotNull(node);
        assertNotNull(node.getPrevious());
        assertEquals((Integer) 96, node.getData());
        node = node.getNext();
        assertNotNull(node);
        assertNotNull(node.getPrevious());
        assertEquals((Integer) 84, node.getData());
        node = node.getNext();
        assertNotNull(node);
        assertNotNull(node.getPrevious());
        assertEquals((Integer) 83, node.getData());
        node = node.getNext();
        assertNotNull(node);
        assertNotNull(node.getPrevious());
        assertEquals((Integer) 23, node.getData());
        assertNotNull(node.getNext());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFront() {
        list.addToFront(84);
        list.addToBack(23);
        list.addAtIndex(1, 83);
        list.addToFront(96);
        list.addAtIndex(3, 58);

        assertEquals(5, list.size());

        assertEquals((Integer) 96, list.removeFromFront());

        assertEquals(4, list.size());

        LinkedListNode<Integer> node = list.getHead();
        assertNotNull(node);
        assertNotNull(node.getPrevious());
        assertEquals((Integer) 84, node.getData());
        assertEquals((Integer) 23, node.getPrevious().getData());
        node = node.getNext();
        assertNotNull(node);
        assertNotNull(node.getPrevious());
        assertEquals((Integer) 83, node.getData());
        node = node.getNext();
        assertNotNull(node);
        assertNotNull(node.getPrevious());
        assertEquals((Integer) 58, node.getData());
        node = node.getNext();
        assertNotNull(node);
        assertNotNull(node.getPrevious());
        assertEquals((Integer) 23, node.getData());
        assertNotNull(node.getNext());
        assertEquals((Integer) 84, node.getNext().getData());
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        list.addToFront(84);
        list.addToBack(23);
        list.addAtIndex(1, 83);
        list.addToFront(96);
        list.addAtIndex(3, 58);

        assertEquals(5, list.size());

        assertEquals((Integer) 23, list.get(4));
    }

    @Test(timeout = TIMEOUT)
    public void testArray() {
        list.addToFront(84);
        list.addToBack(23);
        list.addAtIndex(1, 83);
        list.addToFront(96);
        list.addAtIndex(3, 58);

        assertEquals(5, list.size());

        Integer[] expected = new Integer[] {96, 84, 83, 58, 23};
        assertArrayEquals(expected, list.toArray());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayEmpty() {
        Integer[] expected = new Integer[] {};
        assertArrayEquals(expected, list.toArray());
    }

    @Test(expected = NoSuchElementException.class, timeout = TIMEOUT)
    public void testRemoveNotInList() {
        list.addToFront(84);
        list.addToBack(23);
        list.addAtIndex(1, 83);
        list.addToFront(96);
        list.addAtIndex(3, 58);

        list.removeFirstOccurrence(42);
    }

    @Test(timeout = TIMEOUT)
    public void testConstructor() {
        Integer[] initialValues = new Integer[] {38, 43, 29, 59, 83};

        list = new CircularDoublyLinkedList<>(initialValues);

        assertEquals(5, list.size());

        LinkedListNode<Integer> node = list.getHead();
        assertNotNull(node);
        assertNotNull(node.getPrevious());
        assertEquals((Integer) 38, node.getData());
        node = node.getNext();
        assertNotNull(node);
        assertNotNull(node.getPrevious());
        assertEquals((Integer) 43, node.getData());
        node = node.getNext();
        assertNotNull(node);
        assertNotNull(node.getPrevious());
        assertEquals((Integer) 29, node.getData());
        node = node.getNext();
        assertNotNull(node);
        assertNotNull(node.getPrevious());
        assertEquals((Integer) 59, node.getData());
        node = node.getNext();
        assertNotNull(node);
        assertNotNull(node.getPrevious());
        assertEquals((Integer) 83, node.getData());
        assertNotNull(node.getNext());
    }
}
