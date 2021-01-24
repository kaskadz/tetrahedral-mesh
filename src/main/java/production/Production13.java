package production;

import model.GraphNode;
import model.InteriorNode;
import model.TetrahedralGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Production13 extends AbstractProduction {

    private List<GraphNode> commonCh1, commonCh2;

    @Override
    public int getProductionId() {
        return 13;
    }

    @Override
    public void apply(TetrahedralGraph graph, InteriorNode interiorNode, List<GraphNode> graphNodeList) {

        verifyInteriorNodeIsNull(interiorNode);
        verifyGraphNodeListSize(graphNodeList, 2);

        if (this.meetsProductionRequirements(graph, graphNodeList)) {
            applyProduction(graph);
        } else {
            throwProductionApplicationException("Uuu, nie dziala");
        }
    }

    private boolean isContaining(List<GraphNode> nodes, GraphNode node) {
        return nodes
                .stream()
                .anyMatch(x -> x.getCoordinates().equals(node.getCoordinates()) && !x.getId().equals(node.getId()));
    }

    private List<GraphNode> filterMirroredNodes(List<GraphNode> res, List<GraphNode> nodes) {

        List<GraphNode> r = new ArrayList<>();

        r.add(res.stream().filter(x -> x.getCoordinates().equals(nodes.get(0).getCoordinates()) && !x.getId().equals(nodes.get(0).getId())).findFirst().get());
        r.add(res.stream().filter(x -> x.getCoordinates().equals(nodes.get(1).getCoordinates()) && !x.getId().equals(nodes.get(1).getId())).findFirst().get());

        return r;
    }

    private boolean meetsProductionRequirements(TetrahedralGraph graph, List<GraphNode> nodes) {
        int level = nodes.get(0).getLevel();
        nodes = nodes
                .stream()
                .filter(x -> x.getLevel() == level)
                .collect(Collectors.toList());


        if (nodes.size() != 2) { return false; }


        List<InteriorNode> leftInteriors = nodes
                .stream()
                .flatMap(GraphNode::getInteriors)
                .distinct()
                .collect(Collectors.toList());


        if (leftInteriors.size() == 0 || !leftInteriors.get(0).getParent().isPresent()) {
            return false;
        }

        InteriorNode leftParent = leftInteriors.get(0).getParent().get();

        for(GraphNode s : leftParent.getSiblings().collect(Collectors.toList())) {

            for(InteriorNode i : s.getInteriors().collect(Collectors.toList())) {

                if (i.equals(leftParent) || i.getChildren().count() != 1) {
                    continue;
                }

                InteriorNode interior = i.getChildren().findFirst().get();

                boolean isContained = (isContaining(interior.getSiblings().collect(Collectors.toList()), nodes.get(0)) &&
                                      isContaining(interior.getSiblings().collect(Collectors.toList()), nodes.get(1)));

                if (isContained) {
                    List<GraphNode> sss2 = interior.getSiblings().collect(Collectors.toList());

                    this.commonCh1 = nodes;
                    this.commonCh2 = filterMirroredNodes(sss2, nodes);
                    return true;
                }
            }
        }
            return false;
    }

    private void applyProduction(TetrahedralGraph graph) {

        for(int i=0; i<2; i++) {
            graph.mergeNodes(commonCh2.get(i), commonCh1.get(i));
        }
    }
}
