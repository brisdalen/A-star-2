import java.awt.Point

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

        val nextTo = tiles[1][0]
        println("Cost: ${gCostHandler.calculateG(start.variation, direction(start.position, nextTo.position), nextTo.variation)}")

        return path
    }

    private fun add(p1: Point, p2: Point): Point {
        return Point(p1.x + p2.x, p1.y + p2.y)
    }

    private fun checkSurrounding(start: Node, tiles: Array<Array<Node>>): List<Node> {
        val validSurrounding = mutableListOf<Node>()
        for(surrounding in surrounding) {
            var p = add(start.position, surrounding)
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
}