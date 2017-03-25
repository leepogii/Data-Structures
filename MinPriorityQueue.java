/**
 * Your implementation of a min priority queue.
 * @author Min Ho Lee
 * @version 1.0
 */
public class MinPriorityQueue<T extends Comparable<? super T>>
    implements PriorityQueueInterface<T> {

    private MinHeap<T> backingHeap;
    // Do not add any more instance variables

    /**
     * Creates a priority queue.
     */
    public MinPriorityQueue() {
        backingHeap = new MinHeap<>();
    }

    @Override
    public void enqueue(T item) {
        backingHeap.add(item);
    }

    @Override
    public T dequeue() {
        return backingHeap.remove();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return backingHeap.size();
    }

    @Override
    public void clear() {
        backingHeap.clear();
    }

    /**
     * Used for grading purposes only. Do not use or edit.
     * @return the backing heap
     */
    public MinHeap<T> getBackingHeap() {
        return backingHeap;
    }

}
