package production;

import model.GraphNode;
import model.InteriorNode;
import model.TetrahedralGraph;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Production7 extends AbstractProduction {

    public class Tuple<X, Y> {
        public final X e1;
        public final Y e2;
        public Tuple(X x, Y y) {
            this.e1 = x;
            this.e2 = y;
        }
    }

    private Comparator<GraphNode> byX = Comparator.comparing(
            x -> x.getCoordinates().getX()
    );

    private Comparator<GraphNode> byY = Comparator.comparing(
            x -> x.getCoordinates().getY()
    );


    private List<GraphNode> commonCh1, commonCh2;

    @Override
    public int getProductionId() {
        return 7;
    }

    @Override
    public void apply(TetrahedralGraph graph, InteriorNode interiorNode, List<GraphNode> graphNodeList) {

        verifyInteriorNodeIsNull(interiorNode);
        verifyGraphNodeListSize(graphNodeList, 1);

        GraphNode entryNode = graphNodeList.get(0);

        if (this.meetsProductionRequirements(entryNode)) {
            applyProduction(graph);
        } else {
            throwProductionApplicationException("Uuu, nie dziala");
        }
    }

    private List<Tuple<InteriorNode, InteriorNode>> getNeighborInteriors(List<InteriorNode> interiorNodes) {
        List<Tuple<InteriorNode, InteriorNode>> neighbouredInterior = new ArrayList<>();

        for (int i=0; i < interiorNodes.size(); i++) {
            InteriorNode i1 = interiorNodes.get(i);

            for (int j=i+1; j < interiorNodes.size(); j++) {
                InteriorNode i2 = interiorNodes.get(j);

                List<GraphNode> commonNodes = i1.getSiblings()
                        .filter(x -> i2.getSiblings().collect(Collectors.toList()).contains(x))
                        .collect(Collectors.toList());

                if (commonNodes.size() == 2) {
                    neighbouredInterior.add(new Tuple<>(i1, i2));
//                    neighbouredInterior.add(i1);
//                    neighbouredInterior.add(i2);
////                    return Arrays.asList(i1, i2);
                }
            }
        }
        return neighbouredInterior;
    }

    private boolean existsWithCoords(GraphNode n, Set<GraphNode> nodes) {
        return nodes.stream().anyMatch(x -> x.getId() != n.getId()
                && x.getCoordinates().getX() == n.getCoordinates().getX()
                && x.getCoordinates().getY() == n.getCoordinates().getY());
    }

    private boolean meetsProductionRequirements(GraphNode node) {
        // check if there is at least one level down
        if (node.getGraph().getMaxLevel() < node.getLevel() + 1) return false;

        List<InteriorNode> interiorNodes = node.getInteriors().collect(Collectors.toList());

        if (interiorNodes.size() < 2) return false;

        // check if there is at least one pair of neighbouring interior nodes
        // this pair should have minimum 2 common neighbour

        // neighbouring interior nodes
        List<Tuple<InteriorNode, InteriorNode>> interiors = getNeighborInteriors(interiorNodes);


        // not found any pair of neighbouring interior nodes
        if (interiors.size() == 0) return false;

        for (Tuple<InteriorNode, InteriorNode> neighInt : interiors) {

            List<Tuple<InteriorNode, InteriorNode>> n1Children = getNeighborInteriors(neighInt.e1.getChildren().collect(Collectors.toList()));
            if (n1Children.size() == 0) continue;

            List<Tuple<InteriorNode, InteriorNode>> n2Children = getNeighborInteriors(neighInt.e2.getChildren().collect(Collectors.toList()));
            if (n2Children.size() == 0) continue;


            for (Tuple<InteriorNode, InteriorNode> ch1 : n1Children) {
                for (Tuple<InteriorNode, InteriorNode> ch2 : n2Children) {

                    Set<GraphNode> ch1Nodes = Stream.concat(ch1.e1.getSiblings(), ch1.e2.getSiblings())
                            .collect(Collectors.toSet());

                    Set<GraphNode> ch2Nodes = Stream.concat(ch2.e1.getSiblings(), ch2.e2.getSiblings())
                            .collect(Collectors.toSet());


                    List<GraphNode> commonInCh1 = ch1Nodes.stream()
                            .filter(x -> existsWithCoords(x, ch2Nodes))
                            .sorted(byX.thenComparing(byY))
                            .collect(Collectors.toList());

                    List<GraphNode> commonInCh2 = ch2Nodes.stream()
                            .filter(x -> existsWithCoords(x, ch1Nodes))
                            .sorted(byX.thenComparing(byY))
                            .collect(Collectors.toList());

                    // found 3 graphNodes with same coordinates in each list
                    if (commonInCh1.size() == 3) {
                        double midX = (commonInCh1.get(0).getCoordinates().getX() + commonInCh1.get(2).getCoordinates().getX()) / 2;
                        double midY = (commonInCh1.get(0).getCoordinates().getY() + commonInCh1.get(2).getCoordinates().getY()) / 2;

                        // check if mid coords are correct
                        if (midX == commonInCh1.get(1).getCoordinates().getX() && midY == commonInCh2.get(1).getCoordinates().getY()) {
                            commonCh1 = commonInCh1;
                            commonCh2 = commonInCh2;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void applyProduction(TetrahedralGraph graph) {


        System.out.println("commonCh1 nodes");

        commonCh1.forEach(x -> System.out.printf("id = %s x = %f y = %f\n",
                x.getId(),
                x.getCoordinates().getX(),
                x.getCoordinates().getY()));

        System.out.println("commonCh2 nodes");

        commonCh2.forEach(x -> System.out.printf("id = %s x = %f y = %f\n",
                x.getId(),
                x.getCoordinates().getX(),
                x.getCoordinates().getY()));


        for(int i=0; i<3; i++) {
            graph.mergeNodes(commonCh2.get(i), commonCh1.get(i));
        }
        System.out.println("Applied production 7");

    }
}
