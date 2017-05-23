import java.util.NoSuchElementException;

/**
 * Your implementation of a CircularDoublyLinkedList
 *
 * @author Min Ho Lee
 * @version 1.0
 */
public class CircularDoublyLinkedList<T> implements LinkedListInterface<T> {

    // Do not add new instance variables.
    private LinkedListNode<T> head;
    private int size;

    /**
     * Creates an empty circular doubly-linked list.
     */
    public CircularDoublyLinkedList() {
        head = null;
    }

    /**
     * Creates a circular doubly-linked list with
     * {@code data} added to the list in order.
     * @param data The data to be added to the LinkedList.
     * @throws java.lang.IllegalArgumentException if {@code data} is null or any
     * item in {@code data} is null.
     */
    public CircularDoublyLinkedList(T[] data) {
        if (data == null) {
            throw new IllegalArgumentException("The data passed is null");
        }
        for (int i = 0; i < data.length; i++) {
            if (data[i] == null) {
                throw new IllegalArgumentException("The data passed is null");
            }
        }
        head = new LinkedListNode<>(data[0]);
        head.setNext(head);
        head.setPrevious(head);
        LinkedListNode<T> tmp = head;
        size++;
        for (int i = 1; i < data.length; i++) {
            LinkedListNode<T> toAdd = new LinkedListNode<>(data[i], tmp, head);
            tmp.setNext(toAdd);
            head.setPrevious(toAdd);
            tmp = tmp.getNext();
            size++;
        }
    }

    @Override
    public void addAtIndex(int index, T data) {
        if ((index < 0) || (index > size)) {
            throw new IndexOutOfBoundsException("Index should be positive and"
                + " smaller than size integer.");
        }
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }
        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            LinkedListNode<T> tmp = head;
            for (int i = 0; i < index - 1; i++) {
                tmp = tmp.getNext();
            }
            LinkedListNode<T> toAdd = new LinkedListNode<>(data,
                    tmp, tmp.getNext());
            tmp.getNext().setPrevious(toAdd);
            tmp.setNext(toAdd);
            size++;
        }
    }

    @Override
    public T get(int index) {
        if ((index < 0) || (index >= size)) {
            throw new IndexOutOfBoundsException("Index should be positive and"
                    + " smaller than or equals to the size integer.");
        }
        if (index == 0) {
            return head.getData();
        }
        if (index == (size - 1)) {
            return head.getPrevious().getData();
        }
        LinkedListNode<T> toReturn = head;
        for (int i = 0; i < index; i++) {
            toReturn = toReturn.getNext();
        }
        return toReturn.getData();
    }

    @Override
    public T removeAtIndex(int index) {
        if ((index < 0) || (index >= size)) {
            throw new IndexOutOfBoundsException("Index should be positive and"
                    + " smaller than or equals to the size integer.");
        }
        if (index == 0) {
            return removeFromFront();
        }
        if (index == (size - 1)) {
            return removeFromBack();
        }
        LinkedListNode<T> toRemove = head;
        for (int i = 0; i < index; i++) {
            toRemove = toRemove.getNext();
        }
        T toReturn = toRemove.getData();
        toRemove.getNext().setPrevious(toRemove.getPrevious());
        toRemove.getPrevious().setNext(toRemove.getNext());
        size--;
        return toReturn;
    }

    @Override
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }
        if (head == null) {
            head = new LinkedListNode<>(data);
            head.setNext(head);
            head.setPrevious(head);
        } else {
            LinkedListNode<T> toAdd = new LinkedListNode<>(data,
                    head.getPrevious(), head);
            head.getPrevious().setNext(toAdd);
            head.setPrevious(toAdd);
            head = toAdd;
        }
        size++;
    }

    @Override
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }
        if (head == null) {
            addToFront(data);
        } else {
            LinkedListNode<T> toAdd = new LinkedListNode<>(data,
                head.getPrevious(), head);
            head.getPrevious().setNext(toAdd);
            head.setPrevious(toAdd);
            size++;
        }
    }

    @Override
    public T removeFromFront() {
        if (head == null) {
            return null;
        }
        if (size == 1) {
            T toReturn = head.getData();
            clear();
            return toReturn;
        }
        T toReturn = head.getData();
        head.getNext().setPrevious(head.getPrevious());
        head.getPrevious().setNext(head.getNext());
        head = head.getNext();
        size--;
        return toReturn;
    }

    @Override
    public T removeFromBack() {
        if (head == null) {
            return null;
        }
        if (size == 1) {
            T toReturn = head.getData();
            clear();
            return toReturn;
        }
        T toReturn = head.getPrevious().getData();
        head.getPrevious().getPrevious().setNext(head);
        head.setPrevious(head.getPrevious().getPrevious());
        size--;
        return toReturn;
    }

    @Override
    public int removeFirstOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }
        LinkedListNode<T> tmp = head;
        for (int i = 0; i < size; i++) {
            if (tmp.getData().equals(data)) {
                removeAtIndex(i);
                return i;
            }
            tmp = tmp.getNext();
        }
        throw new NoSuchElementException("There is no matched data");
    }

    @Override
    public boolean removeAllOccurrences(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }
        LinkedListNode<T> tmp = head;
        int tmpSize = size;
        boolean removed = false;
        for (int i = 0; i < tmpSize; i++) {
            if (tmp.getData().equals(data)) {
                if (tmp == head) {
                    removeFromFront();
                } else {
                    tmp.getNext().setPrevious(tmp.getPrevious());
                    tmp.getPrevious().setNext(tmp.getNext());
                    size--;
                }
                removed = true;
            }
            tmp = tmp.getNext();
        }
        return removed;
    }

    @Override
    public Object[] toArray() {
        Object[] toReturn = new Object[size];
        LinkedListNode<T> tmp = head;
        for (int i = 0; i < size; i++) {
            toReturn[i] = tmp.getData();
            tmp = tmp.getNext();
        }
        return toReturn;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        if (head != null) {
            head.setNext(null);
            head.setPrevious(null);
            head = null;
            size = 0;
        }
    }

    /* DO NOT MODIFY THIS METHOD */
    @Override
    public LinkedListNode<T> getHead() {
        return head;
    }
}
