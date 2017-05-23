import java.util.NoSuchElementException;

/**
 * Your implementation of a SinglyLinkedList
 *
 * @author Min Ho Lee
 * @version 1.0
 */
public class SinglyLinkedList<T> implements LinkedListInterface<T> {

    // Do not add new instance variables.
    private LinkedListNode<T> head;
    private int size;

    @Override
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index should be positive "
                    + "integer and smaller or equals to the size.");
        }
        if (data == null) {
            throw new IllegalArgumentException("The data passed is null");
        }
        LinkedListNode<T> toAdd = new LinkedListNode<T>(data);
        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            LinkedListNode<T> tmp = head;
            for (int i = 0; i < index - 1; i++) {
                tmp = tmp.getNext();
            }
            toAdd.setNext(tmp.getNext());
            tmp.setNext(toAdd);
            size++;
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index should be positive "
                + "integer and smaller than the size.");
        }
        if (index == 0) {
            return head.getData();
        }
        LinkedListNode<T> tmp = head;
        for (int i = 0; i < index; i++) {
            tmp = tmp.getNext();
        }
        return tmp.getData();
    }

    @Override
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index should be positive "
                    + "integer and smaller than the size.");
        }
        if (index == 0) {
            return removeFromFront();
        } else if (index == size - 1) {
            return removeFromBack();
        } else {
            LinkedListNode<T> tmp = head;
            for (int i = 0; i < index - 1; i++) {
                tmp = tmp.getNext();
            }
            T toReturn = tmp.getNext().getData();
            tmp.setNext(tmp.getNext().getNext());
            size--;
            return toReturn;
        }
    }
    @Override
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data passed is null");
        }
        LinkedListNode<T> toAdd = new LinkedListNode<T>(data);
        if (head == null) {
            head = toAdd;
        } else {
            toAdd.setNext(head);
            head = toAdd;
        }
        size++;
    }

    @Override
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data passed is null");
        }
        LinkedListNode<T> toAdd = new LinkedListNode<T>(data);
        if (head == null) {
            head = toAdd;
        } else {
            LinkedListNode<T> tmp = head;
            while (tmp.getNext() != null) {
                tmp = tmp.getNext();
            }
            tmp.setNext(toAdd);
        }
        size++;
    }

    @Override
    public T removeFromFront() {
        if (head == null) {
            return null;
        }
        T toReturn = head.getData();
        head = head.getNext();
        size--;
        return toReturn;
    }

    @Override
    public T removeFromBack() {
        if (head == null) {
            return null;
        }
        LinkedListNode<T> tmp = head;
        for (int i = 0; i < size - 1; i++) {
            tmp = tmp.getNext();
        }
        T toReturn = tmp.getNext().getData();
        tmp.setNext(null);
        size--;
        return toReturn;
    }

    @Override
    public int removeFirstOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data passed is null");
        }
        LinkedListNode<T> tmp = head;
        int countIndex = 0;
        while (tmp.getNext() != null) {
            if (data.equals(tmp.getData())) {
                removeAtIndex(countIndex);
                return countIndex;
            }
        }
        throw new NoSuchElementException("No matched element in your list");
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
            head = null;
            size = 0;
        }
    }

    @Override
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }
}
