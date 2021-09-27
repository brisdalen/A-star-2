import java.awt.Point
import java.lang.Exception
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

    /*
    while (openSet.Count > 0) {
			Node node = openSet[0];
			for (int i = 1; i < openSet.Count; i ++) {
				if (openSet[i].fCost < node.fCost || openSet[i].fCost == node.fCost) {
					if (openSet[i].hCost < node.hCost)
						node = openSet[i];
				}
			}

			openSet.Remove(node); X
			closedSet.Add(node); X

			if (node == targetNode) { X
				RetracePath(startNode,targetNode);
				return;
			}

			foreach (Node neighbour in grid.GetNeighbours(node)) {
				if (!neighbour.walkable || closedSet.Contains(neighbour)) {
					continue;
				}

				int newCostToNeighbour = node.gCost + GetDistance(node, neighbour);
				if (newCostToNeighbour < neighbour.gCost || !openSet.Contains(neighbour)) {
					neighbour.gCost = newCostToNeighbour;
					neighbour.hCost = GetDistance(neighbour, targetNode);
					neighbour.parent = node;

					if (!openSet.Contains(neighbour))
						openSet.Add(neighbour);
				}
			}
		}
     */

    fun getPathFromTo(start: Node, goal: Node, tiles: Array<Array<Node>>): List<Node> {
        path.clear()
        // Clear and return the empty list if you're already at the target position
        if(start.position == goal.position) {
            return path
        }
        println("Starting board: ")
        displayDebug(tiles)

        // TODO: Create unit tests for every step of the algorithm, then things should work all together
        val openSet = PriorityQueue(NodeComparator())
        val closedSet = HashSet<Node>()

        for(n in checkSurrounding(start, tiles, closedSet)) {
            println("Cost: ${gCostHandler.calculateG(start.variation, direction(start.position, n.position), n.variation)}")
        }

        openSet.add(start)
        var iters = 0

        while(openSet.isNotEmpty()) {
            println("Iterations: ${++iters}")
            if(iters > 2000) {
                throw Exception("Infinite loop in progress... Aborting")
            }
            var q = openSet.poll()
            closedSet.add(q)

            if(q.position == goal.position) {
                return retracePath(start, q)
            }

            val surrounding = mutableListOf<Node>()
            for(n in checkSurrounding(q, tiles, closedSet)) {
                val node = Node(n.x, n.y, q)
                node.g = node.parent!!.g + gCostHandler.calculateG(q.variation, direction(q.position, node.position), node.variation)
                node.h = gCostHandler.manhattenDistance(node, goal)
                node.setF(node.g + node.h)
                surrounding.add(node)
            }

            for(successor in surrounding) {

                for(node in openSet) {
                    if(node.position == successor.position && node.getF() < successor.getF()) {
                        continue
                    }
                }

                for(node in closedSet) {
                    if(node.position == successor.position && node.getF() < successor.getF()) {
                        continue
                    } else {
                        openSet.add(node)
                    }
                }
            }
        }

        return path
    }

    fun add(p1: Point, p2: Point): Point {
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
}