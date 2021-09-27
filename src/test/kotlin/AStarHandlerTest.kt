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

    lateinit var startNode: Node
    lateinit var goalNode: Node

    @BeforeEach
    fun setUp() {
        startNode = Node(0, 0)
        goalNode = Node(4, 0)
    }

    @Test
    fun getPathFromToShouldReturn0_WhenStartAndEndIsEqual() {
        val expectedSize = 0
        val tiles = TerrainUtils.charInputToNodeMap(tilesInput)

        assertEquals(expectedSize, handler.getPathFromTo(tiles[startNode.y][startNode.x], tiles[startNode.y][startNode.x], tiles).size)
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
        val tiles = TerrainUtils.charInputToNodeMap(tilesInvalid) // TODO: should have an array of Node ready so I dont depend on TerrainUtils
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
    fun getPathFromStartToEnd() {
        val expectedSize = 4;
        val tiles = TerrainUtils.charInputToNodeMap(tilesInput)

        assertEquals(expectedSize, handler.getPathFromTo(tiles[startNode.y][startNode.x], tiles[goalNode.y][goalNode.x], tiles).size)
    }
}