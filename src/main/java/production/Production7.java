package production;

import model.GraphNode;
import model.InteriorNode;
import model.TetrahedralGraph;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Production7 extends AbstractProduction {

    private List<GraphNode> commonCh1, commonCh2;

    @Override
    public int getProductionId() {
        return 7;
    }

    @Override
    public void apply(TetrahedralGraph graph, InteriorNode interiorNode, List<GraphNode> graphNodeList) {

        verifyInteriorNodeIsNull(interiorNode);
        verifyGraphNodeListSize(graphNodeList, 3);

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
        r.add(res.stream().filter(x -> x.getCoordinates().equals(nodes.get(2).getCoordinates()) && !x.getId().equals(nodes.get(2).getId())).findFirst().get());

        return r;
    }

    private boolean meetsProductionRequirements(TetrahedralGraph graph, List<GraphNode> nodes) {
        int level = nodes.get(0).getLevel();
        nodes = nodes
                .stream()
                .filter(x -> x.getLevel() == level)
                .collect(Collectors.toList());


        if (nodes.size() != 3) { return false; }

        List<InteriorNode> leftInteriors = nodes
                .stream()
                .flatMap(GraphNode::getInteriors)
                .distinct()
                .collect(Collectors.toList());


        if (leftInteriors.size() < 2) {
            return false;
        }


        InteriorNode leftParent = leftInteriors.get(0).getParent().get();

        for(GraphNode s : leftParent.getSiblings().collect(Collectors.toList())) {

            for(InteriorNode i : s.getInteriors().collect(Collectors.toList())) {

                if (i.equals(leftParent)) {
                    continue;
                }

                List<InteriorNode> sbs = i.getChildren().collect(Collectors.toList());

                for(int k=0; k<sbs.size(); k++){
                    for(int l=k+1; l<sbs.size(); l++) {

                        boolean upperRight = (isContaining(sbs.get(k).getSiblings().collect(Collectors.toList()), nodes.get(0)) && isContaining(sbs.get(k).getSiblings().collect(Collectors.toList()), nodes.get(1)))
                                || (isContaining(sbs.get(l).getSiblings().collect(Collectors.toList()), nodes.get(0)) && isContaining(sbs.get(l).getSiblings().collect(Collectors.toList()), nodes.get(1)));

                        boolean bottomRight = (isContaining(sbs.get(k).getSiblings().collect(Collectors.toList()), nodes.get(1)) && isContaining(sbs.get(k).getSiblings().collect(Collectors.toList()), nodes.get(2)))
                                || (isContaining(sbs.get(l).getSiblings().collect(Collectors.toList()), nodes.get(1)) && isContaining(sbs.get(l).getSiblings().collect(Collectors.toList()), nodes.get(2))) ;

                        if (upperRight && bottomRight) {
                            List<GraphNode> sss2 = Stream.concat(sbs.get(k).getSiblings(),sbs.get(l).getSiblings()).collect(Collectors.toList());

                            this.commonCh1 = nodes;
                            this.commonCh2 = filterMirroredNodes(sss2, nodes);
                            return true;
                        }
                    }
                }
            }
        }
            return false;
    }

    private void applyProduction(TetrahedralGraph graph) {

        for(int i=0; i<3; i++) {
            graph.mergeNodes(commonCh2.get(i), commonCh1.get(i));
        }

    }
}
