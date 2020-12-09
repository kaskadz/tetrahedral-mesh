# tetrahedral-mesh ![Java CI with Gradle](https://github.com/kaskadz/tetrahedral-mesh/workflows/Java%20CI%20with%20Gradle/badge.svg)
Gramatyka grafowa do rekurencyjnej adaptacji siatek czworościennych

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
