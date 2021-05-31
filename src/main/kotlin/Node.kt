class Node(var x: Int, var y: Int) {

    var edges = arrayOfNulls<Edge>(8)
    var variation = Variation.O

    constructor(x: Int, y: Int, variation: Variation) : this(x, y) {
        this.variation = variation
    }
}