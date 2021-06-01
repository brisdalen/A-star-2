import java.awt.Point

class Node(var x: Int, var y: Int) {

    var edges = arrayOfNulls<Edge>(8)
    val position: Point = Point(x, y)
    var variation = Variation.O

    constructor(x: Int, y: Int, variation: Variation) : this(x, y) {
        this.variation = variation
    }
}