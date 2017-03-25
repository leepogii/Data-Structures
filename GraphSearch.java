import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.List;

/**
 * Your implementations of various graph search algorithms.
 *
 * @author Min Ho Lee
 * @version 1.0
 */
public class GraphSearch {

    /**
     * Searches the Graph passed in as an adjacency list(adjList) to find if a
     * path exists from the start node to the goal node using General Graph
     * Search.
     *
     * Assume the adjacency list contains adjacent nodes of each node in the
     * order they should be added to the Structure. If there are no adjacent
     * nodes, then an empty list is present.
     *
     * The structure(struct) passed in is an empty structure that may behave as
     * a Stack or Queue and this function should execute DFS or BFS on the
     * graph, respectively.
     *
     * DO NOT use {@code instanceof} to determine the type of the Structure!
     *
     * @param start the object representing the node you are starting at.
     * @param struct the Structure you should use to implement the search.
     * @param adjList the adjacency list that represents the graph we are
     *        searching.
     * @param goal the object representing the node we are trying to reach.
     * @param <T> the data type representing the nodes in the graph.
     * @return true if path exists, false otherwise.
     */
    private static <T> boolean generalGraphSearch(T start, Structure<T> struct,
            Map<T, List<T>> adjList, T goal) {
        Set<T> visited = new HashSet<>();
        struct.add(start);

        while (!struct.isEmpty()) {
            T element = struct.remove();
            visited.add(element);
            if (goal.equals(element)) {
                return true;
            }
            for (T e : adjList.get(element)) {
                if (!visited.contains(e)) {
                    struct.add(e);
                }
            }
        }
        return false;
    }

    /**
     * Searches the Graph passed in as an adjacency list(adjList) to find if a
     * path exists from the start node to the goal node using Breadth First
     * Search.
     *
     * Assume the adjacency list contains adjacent nodes of each node in the
     * order they should be added to the Structure. If there are no adjacent
     * nodes, then an empty list is present.
     *
     * This method should be written in one line.
     *
     * @throws IllegalArgumentException if any input is null, or if
     * {@code start} or {@code goal} doesn't exist in the graph
     * @param start the object representing the node you are starting at.
     * @param adjList the adjacency list that represents the graph we are
     *        searching.
     * @param goal the object representing the node we are trying to reach.
     * @param <T> the data type representing the nodes in the graph.
     * @return true if path exists false otherwise
     */
    public static <T> boolean breadthFirstSearch(T start,
            Map<T, List<T>> adjList, T goal) {
        if (start == null || adjList == null || goal == null) {
            throw new IllegalArgumentException("Data can't be null");
        }
        if (!adjList.containsKey(start) || !adjList.containsKey(goal)) {
            throw new IllegalArgumentException("Start or goal doesn't exist");
        }
        Structure<T> struct = new StructureQueue<>();
        return generalGraphSearch(start, struct, adjList, goal);
    }

    /**
     * Searches the Graph passed in as an adjacency list(adjList) to find if a
     * path exists from the start node to the goal node using Depth First
     * Search.
     *
     * Assume the adjacency list contains adjacent nodes of each node in the
     * order they should be added to the Structure. If there are no adjacent
     * nodes, then an empty list is present.
     *
     * This method should be written in one line.
     *
     * @throws IllegalArgumentException if any input is null, or if
     * {@code start} or {@code goal} doesn't exist in the graph
     * @param start the object representing the node you are starting at.
     * @param adjList the adjacency list that represents the graph we are
     *        searching.
     * @param goal the object representing the node we are trying to reach.
     * @param <T> the data type representing the nodes in the graph.
     * @return true if path exists false otherwise
     */
    public static <T> boolean depthFirstSearch(T start,
            Map<T, List<T>> adjList, T goal) {
        if (start == null || adjList == null || goal == null) {
            throw new IllegalArgumentException("Data can't be null");
        }
        if (!adjList.containsKey(start) || !adjList.containsKey(goal)) {
            throw new IllegalArgumentException("Start or goal doesn't exist");
        }
        Structure<T> struct = new StructureStack<>();
        return generalGraphSearch(start, struct, adjList, goal);
    }

    /**
     * Find the shortest distance between the start node and the goal node
     * given a weighted graph in the form of an adjacency list where the
     * edges only have positive weights. If there are no adjacent nodes for
     * a node, then an empty list is present.
     *
     * Return the aforementioned shortest distance if there exists a path
     * between the start and goal, -1 otherwise.
     *
     * There are guaranteed to be no negative edge weights in the graph.
     *
     * You may import/use {@code java.util.PriorityQueue}.
     *
     * @throws IllegalArgumentException if any input is null, or if
     * {@code start} or {@code goal} doesn't exist in the graph
     * @param start the object representing the node you are starting at.
     * @param adjList the adjacency list that represents the graph we are
     *        searching.
     * @param goal the object representing the node we are trying to reach.
     * @param <T> the data type representing the nodes in the graph.
     * @return the shortest distance between the start and the goal node
     */
    public static <T> int dijkstraShortestPathAlgorithm(T start,
            Map<T, List<VertexDistancePair<T>>> adjList, T goal) {
        if (start == null || adjList == null || goal == null) {
            throw new IllegalArgumentException("Data can't be null");
        }
        if (!adjList.containsKey(start) || !adjList.containsKey(goal)) {
            throw new IllegalArgumentException("Start or goal doesn't exist");
        }
        Set<T> visited = new HashSet<>();
        PriorityQueue<VertexDistancePair<T>> pq = new PriorityQueue<>();
        VertexDistancePair<T> vp = new VertexDistancePair<>(start, 0);
        VertexDistancePair<T> curr;
        pq.add(vp);
        while (!pq.isEmpty()) {
            curr = pq.poll();
            if (curr.getVertex().equals(goal)) {
                return curr.getDistance();
            }
            T vert = curr.getVertex();
            if (!visited.contains(vert)) {
                visited.add(vert);

                for (VertexDistancePair<T> e : adjList.get(vert)) {
                    if (!visited.contains(e.getVertex())) {
                        pq.add(new VertexDistancePair<T>(e.getVertex(),
                                curr.getDistance() + e.getDistance()));
                    }
                }
            }
        }
        return -1;
    }

}
