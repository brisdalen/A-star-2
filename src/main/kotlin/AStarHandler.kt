import java.awt.Point
import java.util.*

class AStarHandler {

    val path = mutableListOf<Node>()
    val gCostHandler = CostHandler()

    // 3x3 area to check the surrounding area of a Node
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

    // Clear and return the empty list if you're already at the target position
    fun getPathFromTo(start: Node, goal: Node, tiles: Array<Array<Node>>): List<Node> {
        path.clear()
        if(start.position == goal.position) {
            return path
        }
        println("Starting board: ")
        displayDebug(tiles)

        val openSet = PriorityQueue(NodeComparator()) // 1 Initialize the open list
        val closedSet = HashSet<Node>() // 2 Initialize the closed list

        for(n in checkSurrounding(start, tiles, closedSet)) {
            println("Cost: ${gCostHandler.calculateG(start.variation, direction(start.position, n.position), n.variation)}")
        }

        // put the starting node on the open list (you can leave its f at zero)
        openSet.add(start)
        var iters = 0

        // 3. while the open list is not empty
        // a) find the node with the least f on the open list, call it "q"
        while(openSet.isNotEmpty()) {
            println("Iterations: ${++iters}")
            // b) pop q off the open list
            var q = openSet.poll()

            // c) generate q's 8 successors and set their parents to q
            val surrounding = mutableListOf<Node>()
            for(n in checkSurrounding(q, tiles, closedSet)) {
                val node = Node(n.x, n.y, q)
                node.g = node.parent!!.g + gCostHandler.calculateG(q.variation, direction(q.position, node.position), node.variation)
                node.h = gCostHandler.manhattenDistance(node, goal)
                node.setF(node.g + node.h)
                surrounding.add(node)
            }

            // d) for each successor
            //     i) if successor is the goal, stop search
            //     successor.g = q.g + distance between successor and q
            //     successor.h = distance from goal to successor
            //     successor.f = successor.g + successor.h
            for(successor in surrounding) {
                if(successor.position == goal.position) {
                    successor.g = q.g + gCostHandler.calculateG(q.variation, direction(q.position, successor.position), successor.variation)
                    successor.h = gCostHandler.manhattenDistance(successor, goal)
                    println("[${successor.x},${successor.y}]F:${successor.getF()}")
                    return path
                }

                //ii) if a node with the same position as successor is in the OPEN list which has a
                // lower f than successor, skip this successor
                for(node in openSet) {
                    if(node.position == successor.position && node.getF() < successor.getF()) {
                        continue
                    }
                }

                // iii) if a node with the same position as successor is in the CLOSED list which has
                // a lower f than successor, skip this successor otherwise, add the node to the open list
                for(node in closedSet) {
                    if(node.position == successor.position && node.getF() < successor.getF()) {
                        continue
                    } else {
                        openSet.add(node)
                    }
                }
            }

            // e) push q on the closed list
            closedSet.add(q)
        }

        return path
    }

    private fun add(p1: Point, p2: Point): Point {
        return Point(p1.x + p2.x, p1.y + p2.y)
    }

    private fun checkSurrounding(start: Node, tiles: Array<Array<Node>>, closedSet: Set<Node>): List<Node> {
        val validSurrounding = mutableListOf<Node>()
        for(surrounding in surrounding) {
            val p = add(start.position, surrounding)
            if(isValid(p, tiles)) {
                val node = tiles[p.y][p.x]
                if(!closedSet.contains(node)) {
                    validSurrounding.add(node)
                }
            }
        }
        return validSurrounding
    }

    private fun checkSurroundingDebug(start: Node, tiles: Array<Array<Node>>): List<Node> {
        val validSurrounding = mutableListOf<Node>()
        for(surrounding in surrounding) {
            var p = add(start.position, surrounding)
            println()
            if(isValid(p, tiles)) {
                var node = tiles[p.y][p.x]
                print("[${node.x},${node.y}]")
                validSurrounding.add(node)
            }
        }
        return validSurrounding
    }

    private fun isValid(check: Point, input: Array<Array<Node>>): Boolean {
        return check.x >= 0 && check.x < input.size
                && check.y >= 0 && check.y < input.size
                && input[check.y][check.x].variation != Variation.X
    }

    private fun direction(origin: Point, lookingAt: Point): Direction {
        // Top
        if(origin.y > lookingAt.y) {
            when {
                origin.x > lookingAt.x -> {
                    return Direction.NORTHWEST
                }
                origin.x == lookingAt.x -> {
                    return Direction.NORTH
                }
            }
            return Direction.NORTHEAST
        }
        // Middle
        if(origin.y == lookingAt.y) {
            if(origin.x > lookingAt.x) {
                return Direction.WEST
            }
            return Direction.EAST
        }
        // Bottom
        when {
            origin.x > lookingAt.x -> {
                return Direction.SOUTHWEST
            }
            origin.x == lookingAt.x -> {
                return Direction.SOUTH
            }
        }
        return Direction.SOUTHEAST
    }

    fun display(tiles: Array<Array<Node>>) {
        for(array in tiles) {
            for(n in array) {
                print("${n.variation.name} ")
            }
            println()
        }
    }

    fun displayDebug(tiles: Array<Array<Node>>) {
        for(array in tiles) {
            for(n in array) {
                print("[${n.position.x},${n.position.y}]${n.variation.name} ")
            }
            println()
        }
    }

    internal inner class NodeComparator : Comparator<Node> {
        override fun compare(o1: Node, o2: Node): Int {
            return if(o1.getF() - o2.getF() < 0) -1 else if(o1.getF() == o2.getF()) 0 else 1
        }
    }
}