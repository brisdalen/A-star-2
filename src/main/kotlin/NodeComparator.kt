import java.util.Comparator

class NodeComparator : Comparator<Node> {
    override fun compare(o1: Node, o2: Node): Int {
        return if(o1.getF() - o2.getF() < 0) -1 else if(o1.getF() == o2.getF()) 0 else 1
    }
}