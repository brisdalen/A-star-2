import kotlin.math.abs

class CostHandler {

    companion object {
        fun manhattenDistance(start: Node, goal: Node): Int {
            return abs(start.x - goal.y) + abs(start.y - goal.y)
        }
    }
}