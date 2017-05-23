import java.util.LinkedList;
import java.util.Queue;

/**
 * A Structure implemented like a queue.
 *
 * DO NOT EDIT THIS CLASS!!!
 *
 * @author CS 1332 TAs
 * @version 1.1
 */
public class StructureQueue<T> implements Structure<T> {
    private Queue<T> q = new LinkedList<T>();

    @Override
    public boolean isEmpty() {
        return q.isEmpty();
    }

    @Override
    public void clear() {
        q.clear();
    }

    @Override
    public void add(T node) {
        q.add(node);
    }

    @Override
    public T remove() {
        return q.remove();
    }

    @Override
    public String toString() {
        return "Queue of size " + q.size();
    }
}
