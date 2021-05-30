class Edge(var start: Node, var end: Node) {
    var g = 0
    var h = 0
    private var f = 0

    fun getF(): Int {
        f = g + h
        return f
    }

    fun setF(f: Int) {
        this.f = f
    }
}