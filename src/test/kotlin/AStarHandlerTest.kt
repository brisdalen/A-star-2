import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class AStarHandlerTest {

    var handler = AStarHandler()

    lateinit var startNode: Node
    lateinit var goalNode: Node

    @BeforeEach
    fun setUp() {
        startNode = Node(0, 0)
        goalNode = Node(5, 0)
    }

    @Test
    fun getPathFromToEmpty() {
        val expectedSize = 0

        assertEquals(expectedSize, handler.getPathFromTo(startNode, startNode).size)
    }
}