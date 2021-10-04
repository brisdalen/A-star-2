import java.awt.Point
import java.lang.Exception
import java.util.*

class AStarHandler {

    val path = mutableListOf<Node>()
    val gCostHandler = CostHandler()

    // 3x3 area to check the surrounding area of a Node
    val surroundingGrid: Array<Point> = arrayOf(
        Point(-1, -1), // Top
        Point(0, -1),
        Point(1, -1),
        Point(-1, 0), // Middle
        Point(1, 0),
        Point(-1, 1), // Bottom
        Point(0, 1),
        Point(1, 1)
    )

    /*
    OPEN = priority queue containing START
CLOSED = empty set
while lowest rank in OPEN is not the GOAL:
  current = remove lowest rank item from OPEN
  add current to CLOSED
  for neighbors of current:
    cost = g(current) + movementcost(current, neighbor)
    if neighbor in OPEN and cost less than g(neighbor):
      remove neighbor from OPEN, because new path is better
    if neighbor in CLOSED and cost less than g(neighbor): ⁽²⁾
      remove neighbor from CLOSED
    if neighbor not in OPEN and neighbor not in CLOSED:
      set g(neighbor) to cost
      add neighbor to OPEN
      set priority queue rank to g(neighbor) + h(neighbor)
      set neighbor's parent to current

reconstruct reverse path from goal to start
by following parent pointers
     */

    fun getPathFromTo(start: Node, goal: Node, tiles: Array<Array<Node>>): List<Node> {
        path.clear()
        // Clear and return the empty list if you're already at the target position
        if(start.position == goal.position) {
            return path
        }
        println("Starting position: $start")
        println("Goal position: $goal")
        println("Starting board: ")
        displayDebug(tiles)

        // TODO: Create unit tests for every step of the algorithm, then things should work all together
        val openSet = PriorityQueue(NodeComparator()) // OPEN = priority queue containing START
        val closedSet = HashSet<Node>() // CLOSED = empty set

        openSet.add(start)
        var iters = 0

        while(openSet.isNotEmpty()) {
            println("Iterations: ${++iters}")
            if(iters > 2000) {
                throw Exception("Infinite loop in progress... Aborting")
            }
            var q = openSet.poll() // current = remove lowest rank item from OPEN
            closedSet.add(q) // add current to CLOSED

            displayDebug(tiles, q.position, goal.position)

            // TODO: add preliminary neighbour check for n == goal when current.h == 1?
            if(q.position == goal.position) {
                return retracePath(start, q)
            } // while lowest rank in OPEN is not the GOAL:

            val neighbors = arrayOfNulls<Node>(8)
            val surrounding = checkSurrounding(q, tiles, closedSet)
            for((i, n) in surrounding.withIndex()) {
                if(n != null) {
                    val node = Node(n.x, n.y, n.variation, q)
                    val direction = direction(q.position, node.position)
                    when(direction) {
                        Direction.NORTHEAST -> {
                            node.g = node.parent!!.g + gCostHandler.calculateG(
                                q.variation,
                                direction,
                                node.variation,
                                surrounding[1]?.variation, // north
                                surrounding[4]?.variation // east
                            )
                        }
                        Direction.SOUTHEAST -> {
                            node.g = node.parent!!.g + gCostHandler.calculateG(
                                q.variation,
                                direction,
                                node.variation,
                                surrounding[4]?.variation, // east
                                surrounding[6]?.variation // south
                            )
                        }
                        else -> {
                            node.g = node.parent!!.g + gCostHandler.calculateG(
                                q.variation,
                                direction,
                                node.variation,
                                null,
                                null
                            )
                        }
                    }
                    //node.h = gCostHandler.manhattenDistance(node, goal)
                    node.h = gCostHandler.diagonalDistance(node, goal)
                    node.setF(node.g + node.h)
                    neighbors[i] = node
                }
            }

            for((i, neighbor) in neighbors.withIndex()) { // for neighbors of current:
                if(neighbor != null) {
                    val node = Node(neighbor.x, neighbor.y, neighbor.variation, q)
                    val direction = direction(q.position, node.position)
                    var cardinalVariation1: Variation? = null
                    var cardinalVariation2: Variation? = null
                    when(direction) { // cost = g(current) + movementcost(current, neighbor)
                        Direction.NORTHEAST -> {
                            cardinalVariation1 = surrounding[1]?.variation // north
                            cardinalVariation2 = surrounding[4]?.variation // east
                        }
                        Direction.SOUTHEAST -> {
                            cardinalVariation1 = surrounding[4]?.variation // east
                            cardinalVariation2 = surrounding[6]?.variation // south
                        }
                        else -> {
                            val otherwise = "Otherwise"
                        }
                    }

                    val cost = q.g + gCostHandler.calculateG(
                        q.variation,
                        direction,
                        node.variation,
                        cardinalVariation1,
                        cardinalVariation2
                    )

                    if(openSet.contains(neighbor) && cost < neighbor.g) {
                        openSet.remove(neighbor)
                    }

                    if(closedSet.contains(neighbor) && cost < neighbor.g) {
                        closedSet.remove(neighbor)
                    }

                    if(!openSet.contains(neighbor) && !closedSet.contains(neighbor)) {
                        neighbor.g = cost
                        neighbor.parent = q
                        neighbor.setF(neighbor.g + neighbor.h)
                        openSet.add(neighbor)
                    }
                }
            }
        }

        return path // reconstruct reverse path from goal to start by following parent pointers
    }

    fun add(p1: Point, p2: Point): Point {
        return Point(p1.x + p2.x, p1.y + p2.y)
    }

    private fun checkSurrounding(start: Node, tiles: Array<Array<Node>>, closedSet: Set<Node>): Array<Node?> {
//        val validSurrounding = mutableListOf<Node>() // array instead so I can check cardinals on diagonal movement?
        val surrounding = arrayOfNulls<Node>(8) // array instead so I can check cardinals on diagonal movement?
        for((i, gridValue) in surroundingGrid.withIndex()) {
            val p = add(start.position, gridValue)
            val isValid = isValid(p, tiles)
            if(isValid) {
                val node = tiles[p.y][p.x]
                val closedSetContains = closedSet.contains(node)
                if(!closedSetContains) {
                    surrounding[i] = node
//                    validSurrounding.add(node)
                }
            }
        }
//        return validSurrounding
        return surrounding
    }

    private fun checkSurroundingDebug(start: Node, tiles: Array<Array<Node>>): List<Node> {
        val validSurrounding = mutableListOf<Node>()
        for(surrounding in surroundingGrid) {
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

    fun retracePath(start: Node, end: Node): List<Node> {
        val path = mutableListOf<Node>()
        var currentNode = end

        while(currentNode != start) {
            path.add(currentNode)
            currentNode = currentNode.parent!!
        }
        path.reverse()

        return path
    }

    fun isValid(check: Point, input: Array<Array<Node>>): Boolean {
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

    fun displayDebug(tiles: Array<Array<Node>>, q: Point, goal: Point) {
        for(array in tiles) {
            for(n in array) {
                when (n.position) {
                    goal -> {
                        print("[${n.position.x},${n.position.y}]G ")
                    }
                    q -> {
                        print("[${n.position.x},${n.position.y}]P ")
                    }
                    else -> {
                        print("[${n.position.x},${n.position.y}]${n.variation.name} ")
                    }
                }
            }
            println()
        }
    }
}