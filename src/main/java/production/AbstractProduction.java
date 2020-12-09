package production;

import common.ProductionApplicationException;
import model.GraphNode;
import model.InteriorNode;

import java.util.List;
import java.util.function.Predicate;

public abstract class AbstractProduction implements Production {

    protected void throwProductionApplicationException(String message) {
        throw new ProductionApplicationException(getProductionId(), message);
    }

    protected void verifyInteriorNodeIsNull(InteriorNode interiorNode) {
        if (interiorNode != null) {
            throwProductionApplicationException("interiorNode should be null");
        }
    }

    protected void verifyInteriorNodeIsNotNull(InteriorNode interiorNode) {
        if (interiorNode == null) {
            throwProductionApplicationException("interiorNode should not be null");
        }
    }

    protected void verifyInteriorNodeSymbol(InteriorNode interiorNode, String symbol) {
        verifyInteriorNodeIsNotNull(interiorNode);
        if (!interiorNode.getSymbol().equals(symbol)) {
            throwProductionApplicationException("Invalid interiorNode symbol");
        }
    }

    protected void verifyInteriorNodeIsValid(InteriorNode interiorNode, Predicate<InteriorNode> interiorNodePredicate) {
        if (!interiorNodePredicate.test(interiorNode)) {
            throwProductionApplicationException("Invalid interiorNode");
        }
    }

    protected void verifyGraphNodeListIsEmpty(List<GraphNode> graphNodeList) {
        verifyGraphNodeListIsNotNull(graphNodeList);
        if (!graphNodeList.isEmpty()) {
            throwProductionApplicationException("graphNodeList should be empty");
        }
    }

    protected void verifyGraphNodeListIsNotEmpty(List<GraphNode> graphNodeList) {
        verifyGraphNodeListIsNotNull(graphNodeList);
        if (!graphNodeList.isEmpty()) {
            throwProductionApplicationException("graphNodeList should not be empty");
        }
    }

    protected void verifyGraphNodeListSize(List<GraphNode> graphNodeList, int expectedSize) {
        verifyGraphNodeListIsNotNull(graphNodeList);
        if (graphNodeList.size() == expectedSize) {
            throwProductionApplicationException("graphNodeList is not empty");
        }
    }

    private void verifyGraphNodeListIsNotNull(List<GraphNode> graphNodeList) {
        if (graphNodeList == null) {
            throwProductionApplicationException("graphNodeList should not be null");
        }
    }
}
