package production;

import model.InteriorNode;
import model.Point2d;
import model.TetrahedralGraph;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import processing.Initializer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Production1Tests extends AbstractProductionTest {
    private final Production sut = new Production1();

    @Test
    public void shouldHaveProperNumber() {
        // Arrange
        Production sut = new Production1();

        // Assert
        assertEquals(1, sut.getProductionId());
    }

    @Test
    public void shouldApplyOnInitialGraph() {
        // Arrange
        TetrahedralGraph graph = new Initializer().initializeGraph();

        // Act
        boolean applied = sut.tryApply(graph);

        // Assert
        assertTrue(applied);
    }

    @Test
    public void shouldModifyGraphProperly() {
        // Arrange
        TetrahedralGraph graph = new TetrahedralGraph();
        InteriorNode initialNode = graph.insertInteriorNode(0, "E");

        // Act
        sut.tryApply(graph);

        // Assert
        assertThat(initialNode.getSymbol()).isEqualTo("e");
        assertThat(initialNode.getSiblingsIds()).isEmpty();
        assertThat(initialNode.getChildrenIds()).hasSize(1);

        InteriorNode interiorNode = initialNode.getChildren().findFirst().get();
        assertThat(interiorNode.getSiblingsIds()).hasSize(4);

        // TODO: add siblings check
    }

    @ParameterizedTest
    @MethodSource("invalidGraphs")
    public void shouldNotModifyGraphIfNotApplicable(TetrahedralGraph graph) {
        // Act
        boolean applied = sut.tryApply(graph);

        // Assert
        assertThat(applied).isFalse();
    }

    public static Stream<TetrahedralGraph> invalidGraphs() {
        List<TetrahedralGraph> invalidGraphs = new ArrayList<>();

        invalidGraphs.add(initializeGraph(g -> {
            g.insertInteriorNode(0, "e");
        }));

        invalidGraphs.add(initializeGraph(g -> {
            g.insertGraphNode(0, "E", new Point2d(1.0, 1.0));
        }));

        return invalidGraphs.stream();
    }
}
