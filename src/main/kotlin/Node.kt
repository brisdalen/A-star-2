import java.awt.Point

class Node(var x: Int, var y: Int) {

    val position: Point = Point(x, y)
    var parent: Node? = null
    var variation = Variation.O

    var g = 0
    var h = 0
    private var f = 0

    fun getF(): Int {
        //f = g + h //method should not do more than its name describes
        return f
    }

    fun setF(f: Int) {
        this.f = f
    }

    constructor(x: Int, y: Int, variation: Variation) : this(x, y) {
        this.variation = variation
    }

    constructor(x: Int, y: Int, parent: Node) : this(x, y) {
        this.parent = parent
    }

    constructor(x: Int, y: Int, variation: Variation, parent: Node) : this(x, y) {
        this.variation = variation
        this.parent = parent
    }

    override fun equals(other: Any?): Boolean {
        return if(other is Node) {
            other.position == position
                    && other.variation == variation
                    && other.g == g
        } else false
    }

    override fun toString(): String {
        return "{${x}:${y}}"
    }
}