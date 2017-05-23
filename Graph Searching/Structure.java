/**
 * The interface describing a generic Structure.
 *
 * DO NOT EDIT THIS CLASS!!!
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
public interface Structure<T> {

    /**
     * Checks if the Structure has no elements
     *
     * @return true if the Structure is empty, false otherwise.
     */
    public boolean isEmpty();

    /**
     * Removes all the elements from the Structure.
     */
    public void clear();

    /**
     * Adds node passed in a parameter to the Structure according to the rules
     * of the structure.
     *
     * @param node the element to be added to the Structure.
     */
    public void add(T node);

    /**
     * Removes the node that should be removed next given the rules of the
     * Structure.
     *
     * @return the element that was removed from the Structure.
     */
    public T remove();

}
