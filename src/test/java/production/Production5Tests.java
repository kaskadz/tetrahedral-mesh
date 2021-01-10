package production;

import common.CustomCollectors;
import common.ProductionApplicationException;
import model.InteriorNode;
import model.TetrahedralGraph;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import utils.Production5TestGraphs;
import visualization.MultiStepMultiLevelVisualizer;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class Production5Tests extends AbstractProductionTest {
    private final static Production production5 = new Production5();
    private final MultiStepMultiLevelVisualizer visualizer = new MultiStepMultiLevelVisualizer();
    private final static boolean visualMode = false;

    @Test
    public void shouldHaveProperNumber() {
        // then
        assertEquals(5, production5.getProductionId());
    }

    @Test
    public void shouldApplyOnCorrectLeftSide() {
        // given
        TetrahedralGraph correctGraph = Production5TestGraphs.getProduction5CorrectLeftSide();
        visualizer.addStep(correctGraph);
        InteriorNode interiorNode = correctGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());

        // when
        Executable production = () -> production5.apply(correctGraph, interiorNode, Collections.emptyList());


        // then
        assertDoesNotThrow(production);
        visualizer.addStep(correctGraph);

        // visualize
        if (visualMode) visualizer.displayAll("CorrectLeftSide");
    }

    @Test
    public void shouldChangeSymbolOfInteriorNode() {
        // given
        TetrahedralGraph correctGraph = Production5TestGraphs.getProduction5CorrectLeftSide();
        InteriorNode interiorNode = correctGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());

        // when
        production5.apply(correctGraph, interiorNode, Collections.emptyList());

        // then
        assertEquals(interiorNode.getSymbol(), "i");
    }

    @Test
    public void shouldApplyOnCorrectLeftSideWithAdditionalNodesOnRight() {
        // given
        TetrahedralGraph correctGraph = Production5TestGraphs.getProduction5CorrectLeftSideWithAdditionalNodesOnRight();
        visualizer.addStep(correctGraph);
        InteriorNode interiorNode = correctGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());


        // when
        Executable production = () -> production5.apply(correctGraph, interiorNode, Collections.emptyList());

        // then
        assertDoesNotThrow(production);
        visualizer.addStep(correctGraph);

        // visualize
        if (visualMode) visualizer.displayAll("CorrectLeftSideWithAdditionalNodesOnRight");
    }

    @Test
    public void shouldApplyOnCorrectLeftSideInGreaterGraph() {
        // given
        TetrahedralGraph correctGraph = Production5TestGraphs.getProduction5CorrectLeftSideInGreaterGraph();
        InteriorNode interiorNode = correctGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        visualizer.addStep(correctGraph);

        // when
        Executable production = () -> production5.apply(correctGraph, interiorNode, Collections.emptyList());

        // then
        assertDoesNotThrow(production);
        visualizer.addStep(correctGraph);

        // visualize
        if (visualMode) visualizer.displayAll("CorrectLeftSideInGreaterGraph");
    }

    @Test
    public void shouldNotApplyOnLeftSideWithBrokenLink() {
        // given
        TetrahedralGraph incorrectGraph = Production5TestGraphs.getProduction5LeftSideWithBrokenLink();
        InteriorNode interiorNode = incorrectGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        visualizer.addStep(incorrectGraph);

        // when
        Executable exception = () -> production5.apply(incorrectGraph, interiorNode, Collections.emptyList());

        // then
        assertThrows(ProductionApplicationException.class, exception);

        // visualize
        if (visualMode) visualizer.displayAll("LeftSideWithBrokenLink");
    }

    @Test
    public void shouldNotApplyOnLeftSideWithMissingNode() {
        // given
        TetrahedralGraph incorrectGraph = Production5TestGraphs.getProduction5LeftSideWithMissingNode();
        InteriorNode interiorNode = incorrectGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        visualizer.addStep(incorrectGraph);

        // when
        Executable exception = () -> production5.apply(incorrectGraph, interiorNode, Collections.emptyList());

        // then
        assertThrows(ProductionApplicationException.class, exception);

        // visualize
        if (visualMode) visualizer.displayAll("LeftSideWithMissingNode");
    }

    @Test
    public void shouldNotApplyOnLeftSideWithWrongCoordinates() {
        // given
        TetrahedralGraph incorrectGraph = Production5TestGraphs.getProduction5LeftSideWithIncorrectCoordinates();
        InteriorNode interiorNode = incorrectGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        visualizer.addStep(incorrectGraph);

        // when
        Executable exception = () -> production5.apply(incorrectGraph, interiorNode, Collections.emptyList());

        // then
        assertThrows(ProductionApplicationException.class, exception);

        // visualize
        if (visualMode) visualizer.displayAll("LeftSideWithWrongCoordinates");
    }
}
