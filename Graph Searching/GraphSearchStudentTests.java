import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

/**
 * Student tests for your GraphSearch class.
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
public class GraphSearchStudentTests {
    private static final int TIMEOUT = 200;
    private Map<String, List<String>> adjList;
    private Map<String, List<VertexDistancePair<String>>> weightedGraph;

    @Before
    public void setUp() {
        // Creates a directed, unweighted graph.
        adjList = new HashMap<>();

        List<String> aList = new LinkedList<>();
        aList.add("B");
        aList.add("C");
        aList.add("D");
        adjList.put("A", aList);

        List<String> bList = new LinkedList<>();
        bList.add("H");
        adjList.put("B", bList);

        List<String> cList = new LinkedList<>();
        cList.add("E");
        cList.add("G");
        cList.add("H");
        adjList.put("C", cList);

        List<String> dList = new LinkedList<>();
        dList.add("G");
        adjList.put("D", dList);

        List<String> eList = new LinkedList<>();
        eList.add("F");
        eList.add("H");
        adjList.put("E", eList);

        List<String> fList = new LinkedList<>();
        fList.add("G");
        adjList.put("F", fList);

        List<String> gList = new LinkedList<>();
        adjList.put("G", gList);

        List<String> hList = new LinkedList<>();
        hList.add("F");
        adjList.put("H", hList);

        //Creates an undirected, weighted graph.
        weightedGraph = new HashMap<>();

        List<VertexDistancePair<String>> aListWeighted = new LinkedList<>();
        aListWeighted.add(new VertexDistancePair<String>("B", 8));
        aListWeighted.add(new VertexDistancePair<String>("C", 3));
        weightedGraph.put("A", aListWeighted);

        List<VertexDistancePair<String>> bListWeighted = new LinkedList<>();
        bListWeighted.add(new VertexDistancePair<String>("A", 8));
        bListWeighted.add(new VertexDistancePair<String>("C", 4));
        bListWeighted.add(new VertexDistancePair<String>("D", 11));
        weightedGraph.put("B", bListWeighted);

        List<VertexDistancePair<String>> cListWeighted = new LinkedList<>();
        cListWeighted.add(new VertexDistancePair<String>("A", 3));
        cListWeighted.add(new VertexDistancePair<String>("B", 4));
        cListWeighted.add(new VertexDistancePair<String>("D", 6));
        cListWeighted.add(new VertexDistancePair<String>("E", 7));
        weightedGraph.put("C", cListWeighted);

        List<VertexDistancePair<String>> dListWeighted = new LinkedList<>();
        dListWeighted.add(new VertexDistancePair<String>("B", 11));
        dListWeighted.add(new VertexDistancePair<String>("C", 6));
        dListWeighted.add(new VertexDistancePair<String>("E", 7));
        weightedGraph.put("D", dListWeighted);

        List<VertexDistancePair<String>> eListWeighted = new LinkedList<>();
        eListWeighted.add(new VertexDistancePair<String>("C", 7));
        eListWeighted.add(new VertexDistancePair<String>("D", 7));
        weightedGraph.put("E", eListWeighted);
    }

    @Test(timeout = TIMEOUT)
    public void testDepthFirstSearch() {
        assertTrue(GraphSearch.depthFirstSearch("A", adjList, "G"));
        assertFalse(GraphSearch.depthFirstSearch("H", adjList, "C"));
    }

    @Test(timeout = TIMEOUT)
    public void testBreadthFirstSearch() {
        assertTrue(GraphSearch.breadthFirstSearch("A", adjList, "G"));
        assertFalse(GraphSearch.breadthFirstSearch("H", adjList, "C"));
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstras() {
        assertEquals(7, GraphSearch.dijkstraShortestPathAlgorithm("A",
                    weightedGraph, "B"));
        assertEquals(10, GraphSearch.dijkstraShortestPathAlgorithm("B",
                    weightedGraph, "D"), 10);
        assertEquals(10, GraphSearch.dijkstraShortestPathAlgorithm("A",
                    weightedGraph, "E"), 10);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testVertexNotInGraph() {
        GraphSearch.breadthFirstSearch("J", adjList, "G");
    }
}
