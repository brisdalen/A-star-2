class TerrainUtils {

    companion object {
        fun charInputToNodeMap(input: Array<Array<String>>): Array<Array<Node>> {
            val list: MutableList<Array<Node>> = mutableListOf()

            // For every array in charInput
            for((y, array) in input.withIndex()) {
                val inline: MutableList<Node> = mutableListOf()
                // For every String in the array
                for(x in array.indices) {
                    val variation = Variation.valueOf(array[x])
                    inline.add(Node(x, y, variation))
                }
                list.add(inline.toTypedArray())
            }

            return list.toTypedArray()
        }
    }
}