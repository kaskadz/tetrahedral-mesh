package production;

import common.CustomCollectors;
import common.ProductionApplicationException;
import model.InteriorNode;
import model.TetrahedralGraph;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import utils.Production4TestGraphs;
import utils.Production5TestGraphs;
import visualization.MultiStepMultiLevelVisualizer;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class Production4Tests extends AbstractProductionTest {
    private final static Production production4 = new Production4();
    private final MultiStepMultiLevelVisualizer visualizer = new MultiStepMultiLevelVisualizer();
    private final static boolean visualMode = false;

    @Test
    public void shouldHaveProperNumber() {
        // then
        assertEquals(4, production4.getProductionId());
    }

    @Test
    public void shouldApplyOnCorrectLeftSide() {
        // given
        TetrahedralGraph correctGraph = Production4TestGraphs.getProduction4CorrectLeftSide();
        visualizer.addStep(correctGraph);
        InteriorNode interiorNode = correctGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());

        // when
        Executable production = () -> production4.apply(correctGraph, interiorNode, Collections.emptyList());


        // then
        assertDoesNotThrow(production);
        visualizer.addStep(correctGraph);

        // visualize
        if (visualMode) visualizer.displayAll("CorrectLeftSide");
    }

    @Test
    public void shouldChangeSymbolOfInteriorNode() {
        // given
        TetrahedralGraph correctGraph = Production4TestGraphs.getProduction4CorrectLeftSide();
        InteriorNode interiorNode = correctGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());

        // when
        production4.apply(correctGraph, interiorNode, Collections.emptyList());

        // then
        assertEquals(interiorNode.getSymbol(), "i");
    }

    @Test
    public void shouldApplyOnCorrectLeftSideWithAdditionalNodesOnRight() {
        // given
        TetrahedralGraph correctGraph = Production4TestGraphs.getProduction4CorrectLeftSideWithAdditionalNodesOnRight();
        visualizer.addStep(correctGraph);
        InteriorNode interiorNode = correctGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());


        // when
        Executable production = () -> production4.apply(correctGraph, interiorNode, Collections.emptyList());

        // then
        assertDoesNotThrow(production);
        visualizer.addStep(correctGraph);

        // visualize
        if (visualMode) visualizer.displayAll("CorrectLeftSideWithAdditionalNodesOnRight");
    }

    @Test
    public void shouldApplyOnCorrectLeftSideInGreaterGraph() {
        // given
        TetrahedralGraph correctGraph = Production4TestGraphs.getProduction4CorrectLeftSideInGreaterGraph();
        InteriorNode interiorNode = correctGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        visualizer.addStep(correctGraph);

        // when
        Executable production = () -> production4.apply(correctGraph, interiorNode, Collections.emptyList());

        // then
        assertDoesNotThrow(production);
        visualizer.addStep(correctGraph);

        // visualize
        if (visualMode) visualizer.displayAll("CorrectLeftSideInGreaterGraph");
    }

    @Test
    public void shouldNotApplyOnLeftSideWithBrokenLink() {
        // given
        TetrahedralGraph incorrectGraph = Production4TestGraphs.getProduction4LeftSideWithBrokenLink();
        InteriorNode interiorNode = incorrectGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        visualizer.addStep(incorrectGraph);

        // when
        Executable exception = () -> production4.apply(incorrectGraph, interiorNode, Collections.emptyList());

        // then
        assertThrows(ProductionApplicationException.class, exception);

        // visualize
        if (visualMode) visualizer.displayAll("LeftSideWithBrokenLink");
    }

    @Test
    public void shouldNotApplyOnLeftSideWithMissingNode() {
        // given
        TetrahedralGraph incorrectGraph = Production4TestGraphs.getProduction4LeftSideWithMissingNode();
        InteriorNode interiorNode = incorrectGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        visualizer.addStep(incorrectGraph);

        // when
        Executable exception = () -> production4.apply(incorrectGraph, interiorNode, Collections.emptyList());

        // then
        assertThrows(ProductionApplicationException.class, exception);

        // visualize
        if (visualMode) visualizer.displayAll("LeftSideWithMissingNode");
    }

    @Test
    public void shouldNotApplyOnLeftSideWithWrongCoordinates() {
        // given
        TetrahedralGraph incorrectGraph = Production4TestGraphs.getProduction4LeftSideWithIncorrectCoordinates();
        InteriorNode interiorNode = incorrectGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        visualizer.addStep(incorrectGraph);

        // when
        Executable exception = () -> production4.apply(incorrectGraph, interiorNode, Collections.emptyList());

        // then
        assertThrows(ProductionApplicationException.class, exception);

        // visualize
        if (visualMode) visualizer.displayAll("LeftSideWithWrongCoordinates");
    }
}
