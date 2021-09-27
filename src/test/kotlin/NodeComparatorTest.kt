import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class NodeComparatorTest {

    lateinit var q: PriorityQueue<Node>
    lateinit var n1: Node
    lateinit var n2: Node
    lateinit var n3: Node

    @BeforeEach
    fun setUp() {
        q = PriorityQueue(NodeComparator())
        n1 = Node(0, 0)
        n1.setF(0)

        n2 = Node(10, 20)
        n2.setF(30)

        n3 = Node(100, 200)
        n3.setF(300)

        q.add(n3)
        q.add(n2)
        q.add(n1)
    }

    @Test
    fun qShouldReturnNodeWithLowestFValue() {
        val expected = listOf(0, 30, 300)
        expected.forEach {
            val node = q.poll()
            assertEquals(it, node.getF())
        }
    }
}