/**
 * Use this class to store a generic type representing a vertex in a graph and
 * an integer associated with it representing the distance to this vertex
 * from the previous vertex.
 *
 * DO NOT EDIT THIS CLASS!!!
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
public class VertexDistancePair<A>
        implements Comparable<VertexDistancePair<? super A>> {

    private final A vertex;
    private final int distance;

    /**
     * Constructor for VertexDistancePair.
     *
     * @param vertex the object representing the vertex to be stored.
     * @param vertex the integer representing the distance to be stored.
     */
    public VertexDistancePair(A vertex, int distance) {
        this.vertex = vertex;
        this.distance = distance;
    }

    /**
     * Gets the vertex object stored in this VertexDistancePair.
     *
     * @return the vertex object stored in this VertexDistancePair.
     */
    public A getVertex() {
        return vertex;
    }

    /**
     * Gets the distance stored in the VertexDistancePair.
     *
     * @return the distance stored in the VertexDistancePair.
     */
    public int getDistance() {
        return distance;
    }

    @Override
    public int compareTo(VertexDistancePair<? super A> pair) {
        return this.getDistance() - pair.getDistance();
    }

    @Override
    public String toString() {
        return "Pair with vertex " + vertex + " and distance " + distance;
    }
}
