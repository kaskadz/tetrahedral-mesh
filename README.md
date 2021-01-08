# tetrahedral-mesh ![Java CI with Gradle](https://github.com/kaskadz/tetrahedral-mesh/workflows/Java%20CI%20with%20Gradle/badge.svg)
Gramatyka grafowa do rekurencyjnej adaptacji siatek czworościennych

## Usage
### Command line arugments
The first command line argument should be the id of the processor that is supposed to be run.
### Visualization
Program execution results in displaying of the two windows. One contains the final graph visualization through all the layers with indication of inter-level connections (red & green arrows) and node labels, as well as node colors (interior nodes - orange, exterior nodes - green). The second window contains the same visualizations, but one is present for each step of the reasoning (one per production application).

## Descriptions
### Productions
Productions modify the graph, given the graph, interior node and list of exterior nodes as an indication of the precise place where to apply the production.
### Processors
Processors execute a series of productions. They contain the logic of performing the steps of the grammar reasoning. They take the initial graph as an input and can eventually override it (in special cases).

## Contributing
### Production implementation assignment
For each production implementation assignment, please implement your production according to the following guidelines:
- Place it in package `productions`.
- Name it `ProductionN` where `N` is production number.
- Make it implement `Production` interface.
- Add it to `Productions.productions` array.
- Unit-test the production.
- You can implement a processor to test some production sequence (name it `AssignmentNProcessor` where `N` is a number of assignment that you are testing).

### Assignments from A to D
For each of these assignments, please implement the productions that are not already there, then please implement a proper processor according to the following guidelines:
- Place it in package `processing`.
- Name it `AssignmentNProcessor` where `N` is assignment letter.
- Make it implement `Processor` interface.
- Add it to `Processors.processors` array.

## Used Libraries
- [GraphStream](https://graphstream-project.org/)
- [JUnit5](https://junit.org/junit5/)
- [AssertJ](https://joel-costigliola.github.io/assertj/)
