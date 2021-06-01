import kotlin.math.abs

class CostHandler {
    fun manhattenDistance(start: Node, goal: Node): Int {
        return abs(start.x - goal.y) + abs(start.y - goal.y)
    }

    fun calculateG(originVariation: Variation, direction: Direction, goalVariation: Variation): Int {
        println("calculateG provided with: ${originVariation.name}:${direction.name}:${goalVariation.name}")
        return when(direction) {
            Direction.NORTHWEST -> {
                handleNorthWest(originVariation, goalVariation)
            }
            Direction.NORTH -> {
                handleNorth(originVariation, goalVariation)
            }
            Direction.NORTHEAST -> {
                handleNorthEast(originVariation, goalVariation)
            }
            Direction.WEST -> {
                handleWest(originVariation, goalVariation)
            }
            Direction.EAST -> {
                handleEast(originVariation, goalVariation)
            }
            Direction.SOUTHWEST -> {
                handleSouthWest(originVariation, goalVariation)
            }
            Direction.SOUTH -> {
                handleSouth(originVariation, goalVariation)
            }
            else -> handleSouthEast(originVariation, goalVariation)
        }
    }

    fun handleNorthWest(originVariation: Variation, goalVariation: Variation): Int {
        if(checkContainsNW(originVariation.name, goalVariation.name)) {
            return 999
        }
        return 1
    }

    fun handleNorth(originVariation: Variation, goalVariation: Variation): Int {
        if(checkContainsN(originVariation.name, goalVariation.name)) {
            return 999
        }
        return 1
    }

    fun handleNorthEast(originVariation: Variation, goalVariation: Variation): Int {
        if(checkContainsNE(originVariation.name, goalVariation.name)) {
            return 999
        }
        return 1
    }

    fun handleWest(originVariation: Variation, goalVariation: Variation): Int {
        if(checkContainsW(originVariation.name, goalVariation.name)) {
            return 999
        }
        return 1
    }

    fun handleEast(originVariation: Variation, goalVariation: Variation): Int {
        if(checkContainsE(originVariation.name, goalVariation.name)) {
            return 999
        }
        return 1
    }

    fun handleSouthWest(originVariation: Variation, goalVariation: Variation): Int {
        if(checkContainsSW(originVariation.name, goalVariation.name)) {
            return 999
        }
        return 1
    }

    fun handleSouth(originVariation: Variation, goalVariation: Variation): Int {
        if(checkContainsS(originVariation.name, goalVariation.name)) {
            return 999
        }
        return 1
    }

    fun handleSouthEast(originVariation: Variation, goalVariation: Variation): Int {
        if(checkContainsSE(originVariation.name, goalVariation.name)) {
            return 999
        }
        return 1
    }

    // i.e. goal = (0,0) and origin = (1,1), then this returns true
    private fun checkContainsNW(origin: String, goal: String): Boolean {
        return goal.contains(Variation.S.name)
                || goal.contains(Variation.E.name)
                || goal.contains(Variation.rs.name)
                || goal.contains(Variation.de.name)
                || origin.contains(Variation.N.name)
                || origin.contains(Variation.W.name)
                || origin.contains(Variation.ln.name)
                || origin.contains(Variation.uw.name)
    }

    private fun checkContainsN(origin: String, goal: String): Boolean {
        return goal.contains(Variation.S.name)
                || goal.contains(Variation.ls.name)
                || goal.contains(Variation.rs.name)
                || origin.contains(Variation.N.name)
                || origin.contains(Variation.ln.name)
                || origin.contains(Variation.rn.name)
    }

    private fun checkContainsNE(origin: String, goal: String): Boolean {
        return goal.contains(Variation.S.name)
                || goal.contains(Variation.W.name)
                || goal.contains(Variation.ls.name)
                || goal.contains(Variation.dw.name)
                || origin.contains(Variation.N.name)
                || origin.contains(Variation.E.name)
                || origin.contains(Variation.rn.name)
                || origin.contains(Variation.ue.name)
    }

    private fun checkContainsW(origin: String, goal: String): Boolean {
        return goal.contains(Variation.E.name)
                || goal.contains(Variation.ue.name)
                || goal.contains(Variation.de.name)
                || origin.contains(Variation.W.name)
                || origin.contains(Variation.uw.name)
                || origin.contains(Variation.dw.name)
    }

    private fun checkContainsE(origin: String, goal: String): Boolean {
        return goal.contains(Variation.W.name)
                || goal.contains(Variation.uw.name)
                || goal.contains(Variation.dw.name)
                || origin.contains(Variation.E.name)
                || origin.contains(Variation.ue.name)
                || origin.contains(Variation.de.name)
    }

    private fun checkContainsSW(origin: String, goal: String): Boolean {
        return goal.contains(Variation.N.name)
                || goal.contains(Variation.E.name)
                || goal.contains(Variation.rn.name)
                || goal.contains(Variation.ue.name)
                || origin.contains(Variation.S.name)
                || origin.contains(Variation.W.name)
                || origin.contains(Variation.ls.name)
                || origin.contains(Variation.dw.name)
    }

    private fun checkContainsS(origin: String, goal: String): Boolean {
        return goal.contains(Variation.N.name)
                || goal.contains(Variation.ln.name)
                || goal.contains(Variation.rn.name)
                || origin.contains(Variation.S.name)
                || origin.contains(Variation.ls.name)
                || origin.contains(Variation.rs.name)
    }

    private fun checkContainsSE(origin: String, goal: String): Boolean {
        return goal.contains(Variation.N.name)
                || goal.contains(Variation.W.name)
                || goal.contains(Variation.ln.name)
                || goal.contains(Variation.uw.name)
                || origin.contains(Variation.S.name)
                || origin.contains(Variation.E.name)
                || origin.contains(Variation.rs.name)
                || origin.contains(Variation.de.name)
    }
}