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

    val tilesComplex = arrayOf(
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

    @BeforeEach
    fun setUp() {
        startNode1 = Node(0, 0)
        goalNode1 = Node(4, 0)

        startNode2 = Node(0, 2)
        goalNode2 = Node(4, 2)
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
    fun getPathFromStartToEndWithComplexObstacles() {
        // TODO: Implement
        startNode1 = Node(0, 5)
        goalNode1 = Node(11, 5)

        val tiles = TerrainUtils.charInputToNodeMap(tilesComplex)

        val actual = handler.getPathFromTo(tiles[startNode1.y][startNode1.x], tiles[goalNode1.y][goalNode1.x], tiles)
    }
}