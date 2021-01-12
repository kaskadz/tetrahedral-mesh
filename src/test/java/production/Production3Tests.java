package production;


import common.CustomCollectors;
import common.ProductionApplicationException;
import model.InteriorNode;
import model.TetrahedralGraph;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import utils.Production3TestGraphs;
import visualization.MultiStepMultiLevelVisualizer;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class Production3Tests extends AbstractProductionTest {
    private final Production sut = new Production3();
    private final boolean visualMode = false;
    private final MultiStepMultiLevelVisualizer visualizer = new MultiStepMultiLevelVisualizer();

    @Test
    public void shouldHaveProperNumber() {
        // Act & Assert
        assertEquals(3, sut.getProductionId());
    }

    @Test
    public void shouldApplyOnCorrectLeftSide() {
        // given
        TetrahedralGraph correctGraph = Production3TestGraphs.getProduction3CorrectLeftSide();
        visualizer.addStep(correctGraph);
        InteriorNode interiorNode = correctGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());

        // when
        Executable production = () -> sut.apply(correctGraph, interiorNode, Collections.emptyList());


        // then
        assertDoesNotThrow(production);
        visualizer.addStep(correctGraph);

        // visualize
        if (visualMode) visualizer.displayAll("CorrectLeftSide");
    }

    @Test
    public void shouldApplyOnCorrectLeftSideInGreaterGraph() {
        // given
        TetrahedralGraph correctGraph = Production3TestGraphs.getProduction3CorrectLeftSideInGreaterGraph();
        visualizer.addStep(correctGraph);
        InteriorNode interiorNode = correctGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());

        // when
        Executable production = () -> sut.apply(correctGraph, interiorNode, Collections.emptyList());

        // then
        assertDoesNotThrow(production);
        visualizer.addStep(correctGraph);

        // visualize
        if (visualMode) visualizer.displayAll("CorrectLeftSideInGreaterGraph");
    }

    @Test
    public void shouldNotApplyOnLeftSideWithAdditionalMiddleNode() {
        // given
        TetrahedralGraph incorrectGraph = Production3TestGraphs.getProduction3LeftSideWithAdditionalMiddleNode();
        visualizer.addStep(incorrectGraph);
        InteriorNode interiorNode = incorrectGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());

        // when
        Executable production = () -> sut.apply(incorrectGraph, interiorNode, Collections.emptyList());

        // then
        assertThrows(ProductionApplicationException.class, production);

        // visualize
        if (visualMode) visualizer.displayAll("LeftSideWithAdditionalMiddleNode");
    }

    @Test
    public void shouldNotApplyOnWhenMiddleNodeIsMissed() {
        // given
        TetrahedralGraph incorrectGraph = Production3TestGraphs.getProduction3LeftSideWithMissedMiddleNode();
        visualizer.addStep(incorrectGraph);
        InteriorNode interiorNode = incorrectGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());

        // when
        Executable production = () -> sut.apply(incorrectGraph, interiorNode, Collections.emptyList());

        // then
        assertThrows(ProductionApplicationException.class, production);

        // visualize
        if (visualMode) visualizer.displayAll("LeftSideWithMissedMiddleNode");
    }

    @Test
    public void shouldNotApplyOnWhenMiddleNodeHasWrongCoordinates() {
        // given
        TetrahedralGraph incorrectGraph = Production3TestGraphs.getProduction3LeftSideWithMiddleNodeWithWrongCoordiantes();
        visualizer.addStep(incorrectGraph);
        InteriorNode interiorNode = incorrectGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());

        // when
        Executable production = () -> sut.apply(incorrectGraph, interiorNode, Collections.emptyList());

        // then
        assertThrows(ProductionApplicationException.class, production);

        // visualize
        if (visualMode) visualizer.displayAll("LeftSideWithMiddleNodeWithWrongCoordinates");
    }


}
