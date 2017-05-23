import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;


/**
 * Your implementation of a HashMap, using external chaining as your collision
 * policy.  Read the PDF for more instructions on external chaining.
 *
 * @author Min Ho Lee
 * @version 1.0
 */
public class HashMap<K, V> implements HashMapInterface<K, V> {

    // Do not make any new instance variables.
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries.
     */
    public HashMap() {
        table = new MapEntry[STARTING_SIZE];
    }

    @Override
    public V add(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key or value is null");
        }
        size++;
        if ((double) size / table.length > MAX_LOAD_FACTOR) {
            resizeHelper();
        }
        int hash = Math.abs(key.hashCode()) % table.length;
        if (table[hash] == null) {
            table[hash] = new MapEntry<>(key, value);
        } else {
            if (table[hash].getKey().equals(key)) {
                V toReturn = table[hash].getValue();
                table[hash].setValue(value);
                size--;
                return toReturn;
            }
            MapEntry<K, V> curr = table[hash];
            while (curr.getNext() != null) {
                if (curr.getNext().getKey().equals(key)) {
                    V toReturn = curr.getNext().getValue();
                    curr.getNext().setValue(value);
                    size--;
                    return toReturn;
                }
                curr = curr.getNext();
            }
            curr.setNext(new MapEntry<>(key, value));
            return null;
        }
        return null;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key or value is null");
        }
        int hash = Math.abs(key.hashCode()) % table.length;
        if (table[hash] != null) {
            if (table[hash].getKey().equals(key)) {
                V toReturn = table[hash].getValue();
                table[hash] = table[hash].getNext();
                size--;
                return toReturn;
            }
            MapEntry<K, V> curr = table[hash];
            while (curr.getNext() != null) {
                if (curr.getNext().getKey().equals(key)) {
                    V toReturn = curr.getNext().getValue();
                    curr.setNext(curr.getNext().getNext());
                    size--;
                    return toReturn;
                }
                curr = curr.getNext();
            }
        }
        throw new NoSuchElementException("No element found");
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key or value is null");
        }
        int hash = Math.abs(key.hashCode()) % table.length;
        if (table[hash] != null) {
            if (table[hash].getKey().equals(key)) {
                return table[hash].getValue();
            }
            MapEntry<K, V> curr = table[hash];
            while (curr.getNext() != null) {
                if (curr.getNext().getKey().equals(key)) {
                    return curr.getNext().getValue();
                }
                curr = curr.getNext();
            }
        }
        throw new NoSuchElementException("No element found");
    }

    @Override
    public boolean contains(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key or value is null");
        }
        try {
            get(key);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    @Override
    public void clear() {
        table = new MapEntry[STARTING_SIZE];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> keySet = new HashSet<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                keySet.add(table[i].getKey());
                while (table[i].getNext() != null) {
                    keySet.add(table[i].getNext().getKey());
                    table[i] = table[i].getNext();
                }
            }
        }
        return keySet;
    }

    @Override
    public List<V> values() {
        ArrayList<V> valueList = new ArrayList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                valueList.add(table[i].getValue());
                while (table[i].getNext() != null) {
                    valueList.add(table[i].getNext().getValue());
                    table[i] = table[i].getNext();
                }
            }
        }
        return valueList;
    }

    /**
     * Helper method to resize.
     * Resize to 2 * length of the array + 1
     * when adding if (size + 1) / length of the array
     * is bigger than MAX_LOAD_FACTOR.
     */
    private void resizeHelper() {
        MapEntry<K, V>[] temp = table;
        table = new MapEntry[temp.length * 2 + 1];
        size = 1;
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] != null) {
                add(temp[i].getKey(), temp[i].getValue());
                while (temp[i].getNext() != null) {
                    add(temp[i].getNext().getKey(),
                            temp[i].getNext().getValue());
                    temp[i] = temp[i].getNext();
                }
            }
        }
    }
    /**
     * DO NOT USE THIS METHOD IN YOUR CODE.  IT IS FOR TESTING ONLY
     * @return the backing array of the data structure, not a copy.
     */
    public MapEntry<K, V>[] toArray() {
        return table;
    }

}