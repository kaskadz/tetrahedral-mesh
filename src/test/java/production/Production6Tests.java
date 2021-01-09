package production;

import common.CustomCollectors;
import common.ProductionApplicationException;
import model.InteriorNode;
import model.TetrahedralGraph;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import utils.Production6TestGraphs;
import visualization.MultiStepMultiLevelVisualizer;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class Production6Tests extends AbstractProductionTest {
    private final static Production production6 = new Production6();
    private final MultiStepMultiLevelVisualizer visualizer = new MultiStepMultiLevelVisualizer();
    private final static boolean visualMode = false;

    @Test
    public void shouldHaveProperNumber() {
        // then
        assertEquals(6, production6.getProductionId());
    }

    @Test
    public void shouldApplyOnCorrectLeftSide() {
        // given
        TetrahedralGraph correctGraph = Production6TestGraphs.getProduction6CorrectLeftSide();
        visualizer.addStep(correctGraph);
        InteriorNode interiorNode = correctGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());

        // when
        Executable production = () -> production6.apply(correctGraph, interiorNode, Collections.emptyList());


        // then
        assertDoesNotThrow(production);
        visualizer.addStep(correctGraph);

        // visualize
        if (visualMode) visualizer.displayAll("CorrectLeftSide");
    }

    @Test
    public void shouldChangeSymbolOfInteriorNode() {
        // given
        TetrahedralGraph correctGraph = Production6TestGraphs.getProduction6CorrectLeftSide();
        InteriorNode interiorNode = correctGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());

        // when
        production6.apply(correctGraph, interiorNode, Collections.emptyList());

        // then
        assertEquals(interiorNode.getSymbol(), "i");
    }

    @Test
    public void shouldApplyOnCorrectLeftSideWithAdditionalNodesOnRight() {
        // given
        TetrahedralGraph correctGraph = Production6TestGraphs.getProduction6CorrectLeftSideWithAdditionalNodesOnRight();
        visualizer.addStep(correctGraph);
        InteriorNode interiorNode = correctGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());


        // when
        Executable production = () -> production6.apply(correctGraph, interiorNode, Collections.emptyList());

        // then
        assertDoesNotThrow(production);
        visualizer.addStep(correctGraph);

        // visualize
        if (visualMode) visualizer.displayAll("CorrectLeftSideWithAdditionalNodesOnRight");
    }

    @Test
    public void shouldApplyOnCorrectLeftSideInGreaterGraph() {
        // given
        TetrahedralGraph correctGraph = Production6TestGraphs.getProduction6CorrectLeftSideInGreaterGraph();
        InteriorNode interiorNode = correctGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        visualizer.addStep(correctGraph);

        // when
        Executable production = () -> production6.apply(correctGraph, interiorNode, Collections.emptyList());

        // then
        assertDoesNotThrow(production);
        visualizer.addStep(correctGraph);

        // visualize
        if (visualMode) visualizer.displayAll("CorrectLeftSideInGreaterGraph");
    }

    @Test
    public void shouldNotApplyOnLeftSideWithBrokenLink() {
        // given
        TetrahedralGraph incorrectGraph = Production6TestGraphs.getProduction6LeftSideWithBrokenLink();
        InteriorNode interiorNode = incorrectGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        visualizer.addStep(incorrectGraph);

        // when
        Executable exception = () -> production6.apply(incorrectGraph, interiorNode, Collections.emptyList());

        // then
        assertThrows(ProductionApplicationException.class, exception);

        // visualize
        if (visualMode) visualizer.displayAll("LeftSideWithBrokenLink");
    }

    @Test
    public void shouldNotApplyOnLeftSideWithMissingNode() {
        // given
        TetrahedralGraph incorrectGraph = Production6TestGraphs.getProduction6LeftSideWithMissingNode();
        InteriorNode interiorNode = incorrectGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        visualizer.addStep(incorrectGraph);

        // when
        Executable exception = () -> production6.apply(incorrectGraph, interiorNode, Collections.emptyList());

        // then
        assertThrows(ProductionApplicationException.class, exception);

        // visualize
        if (visualMode) visualizer.displayAll("LeftSideWithMissingNode");
    }

    @Test
    public void shouldNotApplyOnLeftSideWithWrongCoordinates() {
        // given
        TetrahedralGraph incorrectGraph = Production6TestGraphs.getProduction6LeftSideWithIncorrectCoordinates();
        InteriorNode interiorNode = incorrectGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        visualizer.addStep(incorrectGraph);

        // when
        Executable exception = () -> production6.apply(incorrectGraph, interiorNode, Collections.emptyList());

        // then
        assertThrows(ProductionApplicationException.class, exception);

        // visualize
        if (visualMode) visualizer.displayAll("LeftSideWithWrongCoordinates");
    }
}
