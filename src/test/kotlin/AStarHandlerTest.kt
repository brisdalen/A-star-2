import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.awt.Point

internal class AStarHandlerTest {

    var handler = AStarHandler()
    var tilesInput = arrayOf(
        arrayOf("O", "O", "O", "O", "O"),
        arrayOf("O", "O", "E", "O", "O"),
        arrayOf("O", "O", "E", "O", "O"),
        arrayOf("O", "O", "E", "O", "O"),
        arrayOf("O", "O", "O", "O", "O")
    )

    var tilesInput2 = arrayOf(
        arrayOf("O", "O", "O", "O", "O"),
        arrayOf("O", "O", "E", "O", "O"),
        arrayOf("O", "O", "E", "O", "O"),
        arrayOf("O", "O", "E", "O", "O"),
        arrayOf("O", "O", "E", "O", "O")
    )

    val tilesInput3 = arrayOf(
        arrayOf("O", "O", "O", "O", "O", "O", "O"),
        arrayOf("O", "O", "E", "O", "O", "O", "O"),
        arrayOf("O", "O", "E", "O", "O", "O", "O"),
        arrayOf("O", "O", "E", "O", "O", "O", "O"),
        arrayOf("O", "O", "O", "O", "O", "O", "O")
    )

    val tilesLong = arrayOf(
        arrayOf("O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O"),
        arrayOf("O", "O", "E", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O"),
        arrayOf("O", "O", "E", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O"),
        arrayOf("O", "O", "E", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O"),
        arrayOf("O", "O", "E", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O"),
        arrayOf("O", "O", "E", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O"),
        arrayOf("O", "O", "E", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O"),
        arrayOf("O", "O", "E", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O"),
        arrayOf("O", "O", "E", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O")
    )

    val tilesComplex1 = arrayOf(
        arrayOf("O", "O", "W", "O", "O", "O", "E", "O", "O", "O", "O", "O", "O"),
        arrayOf("O", "O", "W", "O", "O", "O", "E", "O", "NW", "NE", "O", "O", "O"),
        arrayOf("O", "O", "W", "O", "O", "O", "E", "O", "O", "E", "O", "O", "O"),
        arrayOf("O", "O", "W", "O", "O", "O", "E", "O", "O", "E", "O", "O", "O"),
        arrayOf("O", "O", "SW", "S", "S", "O", "E", "S", "S", "SE", "O", "W", "O"),
        arrayOf("O", "O", "O", "O", "W", "O", "O", "O", "O", "O", "O", "W", "O"),
        arrayOf("O", "O", "O", "O", "W", "O", "O", "O", "O", "O", "O", "W", "O"),
        arrayOf("O", "O", "O", "O", "SW", "O", "NW", "N", "N", "N", "N", "N", "N"),
        arrayOf("O", "O", "NE", "O", "NS", "O", "W", "O", "O", "O", "O", "O", "O")
    )

    val tilesComplex2 = arrayOf(
        arrayOf("O", "O", "E", "O", "O", "O", "W", "O", "O", "O", "O", "O", "O"),
        arrayOf("O", "O", "E", "O", "O", "O", "W", "O", "O", "E", "O", "O", "O"),
        arrayOf("O", "O", "E", "O", "O", "O", "W", "O", "O", "E", "O", "O", "O"),
        arrayOf("O", "O", "E", "O", "O", "O", "W", "O", "O", "E", "O", "O", "O"),
        arrayOf("O", "O", "E", "S", "S", "O", "W", "S", "S", "S", "O", "W", "O"),
        arrayOf("O", "O", "O", "O", "W", "O", "N", "O", "O", "O", "O", "W", "O"),
        arrayOf("O", "O", "O", "O", "W", "O", "S", "O", "O", "O", "O", "W", "O"),
        arrayOf("O", "O", "O", "O", "W", "O", "W", "N", "N", "N", "N", "N", "N"),
        arrayOf("O", "O", "E", "O", "O", "O", "W", "O", "O", "O", "O", "O", "O")
    )

    val tilesPassage = arrayOf(
        arrayOf("O", "O", "W", "O", "O"),
        arrayOf("O", "O", "N", "O", "O"),
        arrayOf("O", "O", "S", "O", "O"),
        arrayOf("O", "O", "W", "O", "O"),
        arrayOf("O", "O", "W", "O", "O"),
        arrayOf("O", "O", "W", "O", "O"),
        arrayOf("O", "O", "W", "O", "O"),
    )

    val tilesDiagonal = arrayOf(
        arrayOf("O", "rn", "O", "O", "O"),
        arrayOf("ls", "O", "rn", "O", "O"),
        arrayOf("O", "ls", "O", "rn", "O"),
        arrayOf("O", "O", "ls", "O", "rn"),
        arrayOf("O", "O", "O", "ls", "O"),
        arrayOf("O", "O", "O", "O", "ls"),
        arrayOf("O", "O", "O", "O", "O"),
    )

    val tilesCombo1 = arrayOf(
        arrayOf("O", "W", "X", "E", "O"),
        arrayOf("O", "W", "X", "E", "O"),
        arrayOf("O", "W", "X", "E", "O"),
        arrayOf("O", "SW", "S", "SE", "O"),
        arrayOf("O", "O", "O", "O", "O"),
        arrayOf("O", "O", "O", "O", "O"),
        arrayOf("O", "O", "O", "O", "O"),
    )

    val tilesCombo2 = arrayOf(
        arrayOf("O", "O", "X", "X", "O"),
        arrayOf("O", "rndw", "Nuw", "Nls", "rsde"),
        arrayOf("O", "W", "Sdw", "S", "E"),
        arrayOf("O", "W", "O", "O", "O"),
        arrayOf("O", "O", "O", "O", "O"),
        arrayOf("O", "O", "O", "O", "O"),
        arrayOf("O", "O", "O", "O", "O"),
    )

    var tilesInvalid = arrayOf(
        arrayOf("X", "X", "X", "O", "O"),
        arrayOf("X", "O", "X", "O", "O"),
        arrayOf("X", "X", "X", "O", "O"),
        arrayOf("O", "O", "E", "O", "O"),
        arrayOf("O", "O", "O", "O", "O")
    )

    val surrounding: Array<Point> = arrayOf(
        Point(-1, -1), // Top
        Point(0, -1),
        Point(1, -1),
        Point(-1, 0), // Middle
        Point(1, 0),
        Point(-1, 1), // Bottom
        Point(0, 1),
        Point(1, 1)
    )

    lateinit var startNode1: Node
    lateinit var goalNode1: Node

    lateinit var startNode2: Node
    lateinit var goalNode2: Node

    lateinit var startNode3: Node
    lateinit var goalNode3: Node

    @BeforeEach
    fun setUp() {
        startNode1 = Node(0, 0)
        goalNode1 = Node(4, 0)

        startNode2 = Node(0, 2)
        goalNode2 = Node(4, 2)

        startNode3 = Node(0, 2)
        goalNode3 = Node(11, 2)
    }

    @Test
    fun getPathFromToShouldReturn0_WhenStartAndEndIsEqual() {
        val expectedSize = 0
        val tiles = TerrainUtils.charInputToNodeMap(tilesInput)

        assertEquals(
            expectedSize,
            handler.getPathFromTo(tiles[startNode1.y][startNode1.x], tiles[startNode1.y][startNode1.x], tiles).size
        )
    }

    @Test
    fun addShouldReturnCorrectPoint_WhenAddingPoints() {
        var centerPoint = Point(1, 1)
        val expected = arrayOf(
            Point(0, 0),
            Point(1, 0),
            Point(2, 0),
            Point(0, 1),
            Point(2, 1),
            Point(0, 2),
            Point(1, 2),
            Point(2, 2),
        )
        for((i, s) in surrounding.withIndex()) {
            assertEquals(expected[i], handler.add(centerPoint, s))
        }
    }

    @Test
    fun isValidShouldReturnFalse_WhenInvalidOrBlockedTile() {
        val tiles =
            TerrainUtils.charInputToNodeMap(tilesInvalid) // TODO: should have an array of Node ready so I dont depend on TerrainUtils
        var centerPoint = Point(1, 1)

        for(s in surrounding) {
            val p = handler.add(centerPoint, s)
            assertFalse(handler.isValid(p, tiles))
        }
    }

    @Test
    fun getSurroundingShouldReturn0_WhenOnlyInvalidTiles() {

    }

    @Test
    fun getPathFromStartToEndNoObstacles() {
        val expectedSize = 4
        val tiles = TerrainUtils.charInputToNodeMap(tilesInput)
        val tiles2 = TerrainUtils.charInputToNodeMap(tilesLong)

        val actual = handler.getPathFromTo(tiles[startNode1.y][startNode1.x], tiles[goalNode1.y][goalNode1.x], tiles)
        println()
        for(n in actual) {
            print("$n - ")
        }

        goalNode1.x = 10
        val actual2 = handler.getPathFromTo(tiles2[startNode1.y][startNode1.x], tiles2[goalNode1.y][goalNode1.x], tiles2)
        println()
        for(n in actual2) {
            print("$n - ")
        }

        assertEquals(expectedSize, actual.size)
    }

    @Test
    fun getPathFromStartToEndNSPassage() {
        val expectedSize = 2
        val tiles = TerrainUtils.charInputToNodeMap(tilesPassage)
        startNode1.x = 1
        startNode1.y = 1
        goalNode1.x = 3
        goalNode1.y = 1

        val actual = handler.getPathFromTo(tiles[startNode1.y][startNode1.x], tiles[goalNode1.y][goalNode1.x], tiles)
        println()
        for(n in actual) {
            print("$n - ")
        }

        assertEquals(expectedSize, actual.size)
    }

    @Test
    fun getPathFromStartToEndComboWalls1() {
        val tiles = TerrainUtils.charInputToNodeMap(tilesCombo1)
        goalNode1.x = 4
        goalNode1.y = 0

        val actual = handler.getPathFromTo(tiles[startNode1.y][startNode1.x], tiles[goalNode1.y][goalNode1.x], tiles)
        println()
        for(n in actual) {
            print("$n - ")
        }
    }

    @Test
    fun getPathFromStartToEndDiagonalHalfWalls() {
        val tiles = TerrainUtils.charInputToNodeMap(tilesDiagonal)
        goalNode1.x = 4
        goalNode1.y = 4

        val actual = handler.getPathFromTo(tiles[startNode1.y][startNode1.x], tiles[goalNode1.y][goalNode1.x], tiles)
        println()
        for(n in actual) {
            print("$n - ")
        }
    }

    @Test
    fun getPathFromStartToEndComplexComboWalls() {
        val tiles = TerrainUtils.charInputToNodeMap(tilesCombo2)

        val actual = handler.getPathFromTo(tiles[startNode1.y][startNode1.x], tiles[goalNode1.y][goalNode1.x], tiles)
        println()
        for(n in actual) {
            print("$n - ")
        }
    }

    @Test
    fun getPathFromStartToEndNoObstaclesLongMap() {
        val expectedSize = 10
        val tiles = TerrainUtils.charInputToNodeMap(tilesLong)

        goalNode1.x = 10
        val actual = handler.getPathFromTo(tiles[startNode1.y][startNode1.x], tiles[goalNode1.y][goalNode1.x], tiles)
        println()
        for(n in actual) {
            print("$n - ")
        }

        assertEquals(expectedSize, actual.size)
    }

    @Test
    fun getPathFromStartToEndWithObstacles() {
        val expectedSize = 5
        val tiles = TerrainUtils.charInputToNodeMap(tilesInput)
        val tiles2 = TerrainUtils.charInputToNodeMap(tilesInput2)

        val actual = handler.getPathFromTo(tiles[startNode2.y][startNode2.x], tiles[goalNode2.y][goalNode2.x], tiles)
        println()
        for(n in actual) {
            print("$n - ")
        }
        val actual2 =
            handler.getPathFromTo(tiles2[startNode2.y][startNode2.x], tiles2[goalNode2.y][goalNode2.x], tiles2)
        println()
        for(n in actual2) {
            print("$n - ")
        }

        assertEquals(expectedSize, actual.size)
        assertEquals(expectedSize, actual2.size)
    }

    @Test
    fun getPathFromStartToEndWithObstacles2() {
        val startNode = Node(0, 0)
        val endNode = Node(6, 4) // TODO: Fails on x > 4 ???
        val tiles = TerrainUtils.charInputToNodeMap(tilesInput3)

        val actual = handler.getPathFromTo(tiles[startNode.y][startNode.x], tiles[endNode.y][endNode.x], tiles)
        println()
        for(n in actual) {
            print("$n - ")
        }
    }

    @Test
    fun getPathFromStartToEndWithComplexObstacles() {
        startNode1 = Node(0, 0)
        goalNode1 = Node(7, 2)

        val tiles = TerrainUtils.charInputToNodeMap(tilesComplex1)

        val actual = handler.getPathFromTo(tiles[startNode1.y][startNode1.x], tiles[goalNode1.y][goalNode1.x], tiles)
        println()
        for(n in actual) {
            print("$n - ")
        }
    }

    @Test
    fun getPathFromStartToEndWithComplexObstacles2() {
        startNode1 = Node(0, 0)
        goalNode1 = Node(7, 2)

        val tiles = TerrainUtils.charInputToNodeMap(tilesComplex2)

        val actual = handler.getPathFromTo(tiles[startNode1.y][startNode1.x], tiles[goalNode1.y][goalNode1.x], tiles)
        println()
        for(n in actual) {
            print("$n - ")
        }
    }
}