import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CostHandlerTest {

    private val handler = CostHandler()
    private val open = Variation.O
    private val north = Variation.N
    private val leftNorth = Variation.ln
    private val east = Variation.E
    private val upperEast = Variation.de
    private val downEast = Variation.de
    private val south = Variation.S
    private val rightSouth = Variation.rs
    private val west = Variation.W
    private val upperWest = Variation.uw
    private val downWest = Variation.dw

    @Test
    fun testManhattenDistance() {

        val n1 = Node(0, 0)
        val n2 = Node(5, 5)
        val n3 = Node(-5, -5)

        val expected = 10
        assertEquals(expected, handler.manhattenDistance(n1, n2))
        assertEquals(expected, handler.manhattenDistance(n1, n3))
    }

    @Test
    fun handleNorthWest() {
        assertEquals(999, handler.handleNorthWest(open, south))
        assertEquals(999, handler.handleNorthWest(open, east))
        assertEquals(999, handler.handleNorthWest(open, rightSouth))
        assertEquals(999, handler.handleNorthWest(open, downEast))
        assertEquals(999, handler.handleNorthWest(north, open))
        assertEquals(999, handler.handleNorthWest(west, open))
        assertEquals(999, handler.handleNorthWest(leftNorth, open))
        assertEquals(999, handler.handleNorthWest(upperWest, open))

        assertEquals(1, handler.handleNorthWest(open, open))

        assertEquals(1, handler.handleNorthWest(open, north))
        assertEquals(1, handler.handleNorthWest(open, west))
        assertEquals(1, handler.handleNorthWest(open, leftNorth))
        assertEquals(1, handler.handleNorthWest(open, upperWest))
        assertEquals(1, handler.handleNorthWest(south, open))
        assertEquals(1, handler.handleNorthWest(east, open))
        assertEquals(1, handler.handleNorthWest(rightSouth, open))
        assertEquals(1, handler.handleNorthWest(downEast, open))
    }

    @Test
    fun handleNorth() {
    }

    @Test
    fun handleNorthEast() {
    }

    @Test
    fun handleWest() {
    }

    @Test
    fun handleEast() {
        assertEquals(999, handler.handleEast(open, west))
        assertEquals(999, handler.handleEast(open, upperWest))
        assertEquals(999, handler.handleEast(open, downWest))
        assertEquals(999, handler.handleEast(east, open))
        assertEquals(999, handler.handleEast(upperEast, open))
        assertEquals(999, handler.handleEast(downEast, open))

        assertEquals(1, handler.handleEast(open, open))

        assertEquals(1, handler.handleEast(open, east))
        assertEquals(1, handler.handleEast(open, upperEast))
        assertEquals(1, handler.handleEast(open, downEast))
        assertEquals(1, handler.handleEast(west, open))
        assertEquals(1, handler.handleEast(upperWest, open))
        assertEquals(1, handler.handleEast(downWest, open))
    }

    @Test
    fun handleSouthWest() {
    }

    @Test
    fun handleSouth() {
    }

    @Test
    fun handleSouthEast() {
    }
}