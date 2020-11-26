package model;

import org.graphstream.graph.implementations.MultiGraph;

import java.util.HashMap;
import java.util.Map;

public class TetrahedralGraph extends MultiGraph {
    private final Map<Integer, GraphNode> graphNodeLevels = new HashMap<>();
    private final Map<Integer, InteriorNode> interiorNodeLevels = new HashMap<>();

    public TetrahedralGraph(String id) {
        super(id);
    }


}
