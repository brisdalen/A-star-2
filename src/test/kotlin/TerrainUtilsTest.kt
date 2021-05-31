import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class TerrainUtilsTest {

    var tilesInput = arrayOf(
        arrayOf("O", "O", "O", "O", "O"),
        arrayOf("O", "ln", "O", "O", "O"),
        arrayOf("O", "O", "O", "O", "O"),
        arrayOf("O", "O", "O", "uw", "O"),
        arrayOf("O", "O", "O", "O", "O")
    )

    @Test
    fun testCharInputToNodeMap() {
        val result = TerrainUtils.charInputToNodeMap(tilesInput)
        val expectedSize = 5

        assertEquals(5, result.size)
        for(a1 in result) {
            assertEquals(expectedSize, a1.size)
            for(a2 in a1) {
                print("${a2.variation} ")
            }
            println()
        }
    }
}