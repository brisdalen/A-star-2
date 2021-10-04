import kotlin.math.abs
import kotlin.math.min

class CostHandler {
    fun manhattenDistance(start: Node, goal: Node): Int {
        return abs(start.x - goal.x) + abs(start.y - goal.y)
    }

    /*
    dx = abs(current_cell.x – goal.x)
    dy = abs(current_cell.y – goal.y)

    h = D * (dx + dy) + (D2 - 2 * D) * min(dx, dy)
    where D is length of each node(usually = 1) and D2 is diagonal distance between each node (usually = sqrt(2) )
     */
    fun diagonalDistance(start: Node, goal: Node): Int {
        val dx = abs(start.x - goal.x)
        val dy = abs(start.y - goal.y)

        return 10 * (dx + dy) + (14 - 2 * 1) * min(dx, dy)
    }

    // TODO: Think I need to check the 2 additional cardinal directions on diagonal movement, I.e. check variation of north and east tile when checking g cost on a north-east movement (I.e. moving from {2,2} to {3,3})
    fun calculateG(originVariation: Variation, direction: Direction, goalVariation: Variation, cardinalVariation1: Variation?, cardinalVariation2: Variation?): Int {
        return when(direction) {
            Direction.NORTHWEST -> {
                handleNorthWest(originVariation, goalVariation, cardinalVariation1, cardinalVariation2) // TODO: cardinals
            }
            Direction.NORTH -> {
                handleNorth(originVariation, goalVariation)
            }
            Direction.NORTHEAST -> {
                handleNorthEast(originVariation, goalVariation, cardinalVariation1, cardinalVariation2)
            }
            Direction.WEST -> {
                handleWest(originVariation, goalVariation)
            }
            Direction.EAST -> {
                handleEast(originVariation, goalVariation)
            }
            Direction.SOUTHWEST -> {
                handleSouthWest(originVariation, goalVariation,  cardinalVariation1, cardinalVariation2) // TODO: cardinals
            }
            Direction.SOUTH -> {
                handleSouth(originVariation, goalVariation)
            }
            else -> handleSouthEast(originVariation, goalVariation, cardinalVariation1, cardinalVariation2)
        }
    }

    fun handleNorthWest(originVariation: Variation, goalVariation: Variation, cardinalVariation1: Variation?, cardinalVariation2: Variation?): Int {
        if(checkContainsNW(originVariation.name, goalVariation.name)) {
            return 999
        }
        return 14
    }

    fun handleNorth(originVariation: Variation, goalVariation: Variation): Int {
        if(checkContainsN(originVariation.name, goalVariation.name)) {
            return 999
        }
        return 10
    }

    fun handleNorthEast(originVariation: Variation, goalVariation: Variation, cardinalVariation1: Variation?, cardinalVariation2: Variation?): Int {
        if(cardinalVariation1 == null || cardinalVariation2 == null) {
            return 999
        }
        if(checkContainsNE(originVariation.name, goalVariation.name, cardinalVariation1.name, cardinalVariation2.name)) {
            return 999
        }
        return 14
    }

    fun handleWest(originVariation: Variation, goalVariation: Variation): Int {
        if(checkContainsW(originVariation.name, goalVariation.name)) {
            return 999
        }
        return 10
    }

    fun handleEast(originVariation: Variation, goalVariation: Variation): Int {
        if(checkContainsE(originVariation.name, goalVariation.name)) {
            return 999
        }
        return 10
    }

    fun handleSouthWest(originVariation: Variation, goalVariation: Variation, cardinalVariation1: Variation?, cardinalVariation2: Variation?): Int {
//        if(cardinalVariation1 == null || cardinalVariation2 == null) { // TODO: add cardinals
//            return 999
//        }
        if(checkContainsSW(originVariation.name, goalVariation.name)) { // TODO: add to checkContains
            return 999
        }
        return 14
    }

    fun handleSouth(originVariation: Variation, goalVariation: Variation): Int {
        if(checkContainsS(originVariation.name, goalVariation.name)) {
            return 999
        }
        return 10
    }

    fun handleSouthEast(originVariation: Variation, goalVariation: Variation, cardinalVariation1: Variation?, cardinalVariation2: Variation?): Int {
        if(cardinalVariation1 == null || cardinalVariation2 == null) {
            return 999
        }
        if(checkContainsSE(originVariation.name, goalVariation.name, cardinalVariation1.name, cardinalVariation2.name)) { // east, south
            return 999
        }
        return 14
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

    private fun checkContainsNE(origin: String, goal: String, cardinalNorth: String, cardinalEast: String): Boolean {
        return goal.contains(Variation.S.name)
                || goal.contains(Variation.W.name)
                || goal.contains(Variation.ls.name)
                || goal.contains(Variation.dw.name)
                || origin.contains(Variation.N.name)
                || origin.contains(Variation.E.name)
                || origin.contains(Variation.rn.name)
                || origin.contains(Variation.ue.name)
                || cardinalNorth.contains(Variation.S.name)
                || cardinalNorth.contains(Variation.rs.name)
                || cardinalNorth.contains(Variation.E.name)
                || cardinalNorth.contains(Variation.de.name)
                || cardinalEast.contains(Variation.W.name)
                || cardinalEast.contains(Variation.uw.name)
                || cardinalEast.contains(Variation.N.name)
                || cardinalEast.contains(Variation.ln.name)
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

    private fun checkContainsSE(origin: String, goal: String, cardinalEast: String, cardinalSouth: String): Boolean {
        return goal.contains(Variation.N.name)
                || goal.contains(Variation.W.name)
                || goal.contains(Variation.ln.name)
                || goal.contains(Variation.uw.name)
                || origin.contains(Variation.S.name)
                || origin.contains(Variation.E.name)
                || origin.contains(Variation.rs.name)
                || origin.contains(Variation.de.name)
                || cardinalEast.contains(Variation.W.name)
                || cardinalEast.contains(Variation.dw.name)
                || cardinalEast.contains(Variation.S.name)
                || cardinalEast.contains(Variation.ls.name)
                || cardinalSouth.contains(Variation.N.name)
                || cardinalSouth.contains(Variation.rn.name)
                || cardinalSouth.contains(Variation.E.name)
                || cardinalSouth.contains(Variation.ue.name)
    }
}