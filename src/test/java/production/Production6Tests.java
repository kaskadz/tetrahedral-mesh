package production;

import common.CustomCollectors;
import common.ProductionApplicationException;
import model.InteriorNode;
import model.TetrahedralGraph;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.images.Resolutions;
import org.graphstream.ui.swing.util.SwingFileSinkImages;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import utils.TestGraphs;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class Production6Tests extends AbstractProductionTest {
    private final static String outputDirectory = "src/test/java/output/assignment4/";
    private final static String inputFile = "input.png";
    private final static String outputFile = "output.png";
    private final static Production production6 = new Production6();
    private static FileSinkImages fileSinkImages;

    @BeforeAll
    public static void setUp() {
        fileSinkImages = new SwingFileSinkImages(FileSinkImages.OutputType.PNG, Resolutions.VGA);
        fileSinkImages.setLayoutPolicy(FileSinkImages.LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);
    }

    @Test
    public void shouldHaveProperNumber() {
        // then
        assertEquals(6, production6.getProductionId());
    }

    @Test
    public void shouldApplyOnCorrectLeftSide() throws IOException {
        // given
        String currentDirectory = "correctLeftSide/";
        TetrahedralGraph correctGraph = TestGraphs.getProduction6CorrectLeftSide();
        InteriorNode interiorNode = correctGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        fileSinkImages.writeAll(correctGraph.getGraph(), outputDirectory + currentDirectory + inputFile);

        // when
        Executable production = () -> production6.apply(correctGraph, interiorNode, Collections.emptyList());

        // then
        assertDoesNotThrow(production);
        fileSinkImages.writeAll(correctGraph.getGraph(), outputDirectory + currentDirectory + outputFile);
    }

    @Test
    public void shouldChangeSymbolOfInteriorNode() {
        // given
        TetrahedralGraph correctGraph = TestGraphs.getProduction6CorrectLeftSide();
        InteriorNode interiorNode = correctGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());

        // when
        production6.apply(correctGraph, interiorNode, Collections.emptyList());

        // then
        assertEquals(interiorNode.getSymbol(), "i");
    }

    @Test
    public void shouldApplyOnCorrectLeftSideWithAdditionalNodesOnRight() throws IOException {
        // given
        String currentDirectory = "correctLeftSideWithAdditionalNodesOnRight/";
        TetrahedralGraph correctGraph = TestGraphs.getProduction6CorrectLeftSideWithAdditionalNodesOnRight();
        InteriorNode interiorNode = correctGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        fileSinkImages.writeAll(correctGraph.getGraph(), outputDirectory + currentDirectory + inputFile);

        // when
        Executable production = () -> production6.apply(correctGraph, interiorNode, Collections.emptyList());

        // then
        assertDoesNotThrow(production);
        fileSinkImages.writeAll(correctGraph.getGraph(), outputDirectory + currentDirectory + outputFile);
    }

    @Test
    public void shouldNotApplyOnLeftSideWithBrokenLink() throws IOException {
        // given
        String currentDirectory = "leftSideWithBrokenLink/";
        TetrahedralGraph incorrectGraph = TestGraphs.getProduction6LeftSideWithBrokenLink();
        InteriorNode interiorNode = incorrectGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        fileSinkImages.writeAll(incorrectGraph.getGraph(), outputDirectory + currentDirectory + inputFile);

        // when
        Executable exception = () -> production6.apply(incorrectGraph, interiorNode, Collections.emptyList());

        // then
        assertThrows(ProductionApplicationException.class, exception);
        fileSinkImages.writeAll(incorrectGraph.getGraph(), outputDirectory + currentDirectory + outputFile);
    }

    @Test
    public void shouldNotApplyOnLeftSideWithMissingNode() throws IOException {
        // given
        String currentDirectory = "leftSideWithMissingNode/";
        TetrahedralGraph incorrectGraph = TestGraphs.getProduction6LeftSideWithMissingNode();
        InteriorNode interiorNode = incorrectGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        fileSinkImages.writeAll(incorrectGraph.getGraph(), outputDirectory + currentDirectory + inputFile);

        // when
        Executable exception = () -> production6.apply(incorrectGraph, interiorNode, Collections.emptyList());

        // then
        assertThrows(ProductionApplicationException.class, exception);
        fileSinkImages.writeAll(incorrectGraph.getGraph(), outputDirectory + currentDirectory + outputFile);
    }

    @Test
    public void shouldNotApplyOnLeftSideWithWrongCoordinates() throws IOException {
        // given
        String currentDirectory = "leftSideWithWrongCoordinates/";
        TetrahedralGraph incorrectGraph = TestGraphs.getProduction6LeftSideWithIncorrectCoordinates();
        InteriorNode interiorNode = incorrectGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        fileSinkImages.writeAll(incorrectGraph.getGraph(), outputDirectory + currentDirectory + inputFile);

        // when
        Executable exception = () -> production6.apply(incorrectGraph, interiorNode, Collections.emptyList());

        // then
        assertThrows(ProductionApplicationException.class, exception);
        fileSinkImages.writeAll(incorrectGraph.getGraph(), outputDirectory + currentDirectory + outputFile);
    }

    @Test
    public void shouldApplyOnCorrectLeftSideInGreaterGraph() throws IOException {
        // given
        String currentDirectory = "correctLeftSideInGreaterGraph/";
        TetrahedralGraph correctGraph = TestGraphs.getProduction6CorrectLeftSideInGreaterGraph();
        InteriorNode interiorNode = correctGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        fileSinkImages.writeAll(correctGraph.getGraph(), outputDirectory + currentDirectory + inputFile);

        // when
        Executable production = () -> production6.apply(correctGraph, interiorNode, Collections.emptyList());

        // then
        assertDoesNotThrow(production);
        fileSinkImages.writeAll(correctGraph.getGraph(), outputDirectory + currentDirectory + outputFile);
    }
}
