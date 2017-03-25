import java.util.NoSuchElementException;

/**
 * Your implementation of a min heap.
 * @author Min Ho Lee
 * @version 1.0
 */
public class MinHeap<T extends Comparable<? super T>>
    implements HeapInterface<T> {

    private T[] backingArray;
    private int size;
    // Do not add any more instance variables

    /**
     * Creates a Heap.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[STARTING_SIZE];
    }

    @Override
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("The item is null.");
        }
        size++;
        if (size == backingArray.length) {
            T[] temp = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 0; i < backingArray.length; i++) {
                temp[i] = backingArray[i];
            }
            backingArray = temp;
        }
        int parIndex = size / 2;
        int index = size;
        backingArray[size] = item;
        while (parIndex >= 1) {
            if (backingArray[index].compareTo(backingArray[parIndex]) < 0) {
                backingArray[index] = backingArray[parIndex];
                backingArray[parIndex] = item;
            }
            index = index / 2;
            parIndex = index / 2;
        }
    }

    @Override
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is empty");
        }
        T toReturn = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        removeHelper();
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
        backingArray = (T[]) new Comparable[STARTING_SIZE];
        size = 0;
    }

    /**
     * Helper method of remove()
     */
    private void removeHelper() {
        int parIndex = 1;
        int left = parIndex * 2;
        int right = parIndex * 2 + 1;
        boolean done = false;
        while (!done) {
            T parData = backingArray[parIndex];
            if (left > size) {
                done = true;
            } else if (left == size) {
                if (backingArray[left].compareTo(parData) < 0) {
                    backingArray[parIndex] = backingArray[left];
                    backingArray[left] = parData;
                }
                done = true;
            } else if (right <= size) {
                if (parData.compareTo(backingArray[left]) < 0
                        && parData.compareTo(backingArray[right]) < 0) {
                    done = true;
                } else if (backingArray[left].compareTo(backingArray[right]) < 0
                        && backingArray[left].compareTo(parData) < 0) {
                    backingArray[parIndex] = backingArray[left];
                    backingArray[left] = parData;
                    parIndex = left;
                } else if (backingArray[right].compareTo(backingArray[left]) < 0
                        && backingArray[right].compareTo(parData) < 0) {
                    backingArray[parIndex] = backingArray[right];
                    backingArray[right] = parData;
                    parIndex = right;
                }
                left = parIndex * 2;
                right = parIndex * 2 + 1;
            }
        }
    }
    /**
     * Used for grading purposes only. Do not use or edit.
     * @return the backing array
     */
    public Comparable[] getBackingArray() {
        return backingArray;
    }

}
