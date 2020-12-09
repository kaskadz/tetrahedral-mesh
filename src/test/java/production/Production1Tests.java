package production;

import common.CustomCollectors;
import common.ProductionApplicationException;
import model.InteriorNode;
import model.Point2d;
import model.TetrahedralGraph;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import initialization.EntrySymbolInitializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class Production1Tests extends AbstractProductionTest {
    private final Production sut = new Production1();

    @Test
    public void shouldHaveProperNumber() {
        // Arrange
        Production sut = new Production1();

        // Act & Assert
        assertEquals(1, sut.getProductionId());
    }

    @Test
    public void shouldApplyOnInitialGraph() {
        // Arrange
        TetrahedralGraph graph = new EntrySymbolInitializer().initializeGraph();
        InteriorNode initialNode = graph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());

        // Act
        Executable production = () -> sut.apply(graph, initialNode, Collections.emptyList());

        // Assert
        assertDoesNotThrow(production);
    }

    @Test
    public void shouldModifyGraphProperly() {
        // Arrange
        TetrahedralGraph graph = new TetrahedralGraph();
        InteriorNode initialNode = graph.insertInteriorNode(0, "E");

        // Act
        sut.apply(graph, initialNode, Collections.emptyList());

        // Assert
        assertThat(initialNode.getSymbol()).isEqualTo("e");
        assertThat(initialNode.getSiblingsIds()).isEmpty();
        assertThat(initialNode.getChildrenIds()).hasSize(1);

        InteriorNode interiorNode = initialNode.getChildren().collect(CustomCollectors.toSingle());
        assertThat(interiorNode.getSiblingsIds()).hasSize(4);

        // TODO: add siblings check
    }

    @ParameterizedTest
    @MethodSource("invalidGraphs")
    public void shouldNotModifyGraphIfNotApplicable(TetrahedralGraph graph) {
        // Act
        Executable production = () -> sut.apply(graph, null, Collections.emptyList());

        // Assert
        assertThrows(ProductionApplicationException.class, production);
    }

    public static Stream<TetrahedralGraph> invalidGraphs() {
        List<TetrahedralGraph> invalidGraphs = new ArrayList<>();

        invalidGraphs.add(initializeGraph(g -> {
            g.insertInteriorNode(0, "e");
        }));

        invalidGraphs.add(initializeGraph(g -> {
            g.insertGraphNode(0, "E", new Point2d(1.0, 1.0));
        }));

        // TODO: add more cases

        return invalidGraphs.stream();
    }
}
