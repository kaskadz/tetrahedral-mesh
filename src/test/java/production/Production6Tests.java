package production;

import common.CustomCollectors;
import common.ProductionApplicationException;
import model.InteriorNode;
import model.TetrahedralGraph;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import utils.TestGraphs;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class Production6Tests extends AbstractProductionTest {
    private final static Production production6 = new Production6();

    @Test
    public void shouldHaveProperNumber() {
        // then
        assertEquals(6, production6.getProductionId());
    }

    @Test
    public void shouldApplyOnCorrectLeftSide() {
        // given
        TetrahedralGraph correctGraph = TestGraphs.getProduction6CorrectLeftSide();
        InteriorNode interiorNode = correctGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());

        // when
        Executable production = () -> production6.apply(correctGraph, interiorNode, Collections.emptyList());

        // then
        assertDoesNotThrow(production);
    }

    @Test
    public void shouldApplyOnCorrectLeftSideWithAdditionalNodesOnRight() {
        // given
        TetrahedralGraph correctGraph = TestGraphs.getProduction6CorrectLeftSideWithAdditionalNodesOnRight();
        InteriorNode interiorNode = correctGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());

        // when
        Executable production = () -> production6.apply(correctGraph, interiorNode, Collections.emptyList());

        // then
        assertDoesNotThrow(production);
    }

    @Test
    public void shouldNotApplyOnLeftSideWithBrokenLink() {
        // given
        TetrahedralGraph incorrectGraph = TestGraphs.getProduction6LeftSideWithBrokenLink();
        InteriorNode interiorNode = incorrectGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());

        // when
        Executable exception = () -> production6.apply(incorrectGraph, interiorNode, Collections.emptyList());

        // then
        assertThrows(ProductionApplicationException.class, exception);
    }

    @Test
    public void shouldNotApplyOnLeftSideWithMissingNode() {
        // given
        TetrahedralGraph incorrectGraph = TestGraphs.getProduction6LeftSideWithMissingNode();
        InteriorNode interiorNode = incorrectGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());

        // when
        Executable exception = () -> production6.apply(incorrectGraph, interiorNode, Collections.emptyList());

        // then
        assertThrows(ProductionApplicationException.class, exception);
    }
}
