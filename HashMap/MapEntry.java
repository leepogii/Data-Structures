/**
 * A class representing a map entry for a hash map, with the ability to link to
 * another {@code MapEntry} object.
 *
 * DO NOT CHANGE THIS FILES.
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
public class MapEntry<K, V> {
    private K key;
    private V value;
    private MapEntry<K, V> next;

    /**
     * Create a MapEntry object with the given key and value.
     *
     * @param k key for this entry.
     * @param v value for this entry.
     */
    public MapEntry(K k, V v) {
        key = k;
        value = v;
    }

    /**
     * Gets the key held by this entry.
     *
     * @return key in this entry.
     */
    public K getKey() {
        return key;
    }

    /**
     * Sets the key held by this entry.
     *
     * @param key key to store in this entry.
     */
    public void setKey(K key) {
        this.key = key;
    }

    /**
     * Gets the value held by this entry.
     *
     * @return value in this entry.
     */
    public V getValue() {
        return value;
    }

    /**
     * Sets the value held by this entry.
     *
     * @param value value to store in this entry.
     */
    public void setValue(V value) {
        this.value = value;
    }

    /**
     * Gets the next MapEntry in this chain.
     *
     * @return the next MapEntry in the chain.
     */
    public MapEntry<K, V> getNext() {
        return next;
    }

    /**
     * Sets the next MapEntry in this chain.
     *
     * @param next the MapEntry to set as next in the chain.
     */
    public void setNext(MapEntry<K, V> next) {
        this.next = next;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MapEntry)) {
            return false;
        } else {
            MapEntry<K, V> that = (MapEntry<K, V>) o;
            return that.getKey().equals(key) && that.getValue().equals(value);
        }
    }

    @Override
    public String toString() {
        return key.toString() + ": " + value.toString();
    }
}
