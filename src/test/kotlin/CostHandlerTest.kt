import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CostHandlerTest {

    @Test
    fun testManhattenDistance() {

        var n1 = Node(0, 0)
        var n2 = Node(5, 5)
        var n3 = Node(-5, -5)

        val expected = 10
        assertEquals(expected, CostHandler.manhattenDistance(n1, n2))
        assertEquals(expected, CostHandler.manhattenDistance(n1, n3))
    }
}