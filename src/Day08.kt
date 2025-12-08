import java.util.LinkedList
import java.util.TreeSet
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    data class Point(val x: Int, val y: Int, val z: Int) {
        override fun toString(): String {
            return "$x,$y,$z"
        }
    }

    class Graph private constructor(
        val edges: Map<Point, LinkedList<Point>>
    ) {
        constructor(points: List<Point>) : this(points.associateWith { LinkedList() })

        val nodes: List<Point> get() = edges.keys.toList()

        fun addEdge(p1: Point, p2: Point) {
            edges[p1]!!.add(p2)
            edges[p2]!!.add(p1)
        }
    }


    fun distance(p1: Point, p2: Point): Double {
        val xd = (p1.x - p2.x).toDouble().pow(2)
        val yd = (p1.y - p2.y).toDouble().pow(2)
        val zd = (p1.z - p2.z).toDouble().pow(2)
        return sqrt(xd + yd + zd)
    }

    data class PairOfPoints(val p1: Point, val p2: Point) {
        override fun hashCode(): Int {
            return setOf(p1, p2).hashCode()
        }

        override fun equals(other: Any?): Boolean {
            if (other == null) return false
            if (other !is PairOfPoints) return false
            return setOf(p1, p2).equals(setOf(other.p1, other.p2))
        }

        fun distance() = distance(p1, p2)
    }

    fun subgraph(graph: Graph, root: Point, visitedPoints: MutableSet<Point>): Set<Point> {
        visitedPoints += root
        return graph.edges[root].orEmpty().fold(setOf(root)) { subgraph, point ->
            if (point in visitedPoints) {
                subgraph
            } else {
                subgraph + subgraph(graph, point, visitedPoints)
            }
        }
    }

    fun subgraphs(graph: Graph): Set<Set<Point>> {
        val visitedPoints: MutableSet<Point> = mutableSetOf()
        val subgraphs = mutableSetOf<Set<Point>>()
        graph.nodes.forEach { point ->
            if (point in visitedPoints) return@forEach
            val subgraph = subgraph(graph, point, visitedPoints)
            subgraphs += subgraph
        }
        return subgraphs
    }

    fun part1(input: List<String>, steps: Int): Int {
        val points = input.map { line ->
            val (x, y, z) = line.split(',').map { it.toInt() }
            Point(x, y, z)
        }
        val sortedPairs =
            points.foldIndexed(TreeSet<PairOfPoints>(compareBy { it.distance() })) { index1, sortedPairs, point1 ->
                points.forEachIndexed { index2, point2 ->
                    if (index1 == index2) return@forEachIndexed
                    val pair = PairOfPoints(point1, point2)
                    if (pair in sortedPairs) return@forEachIndexed
                    sortedPairs.add(pair)
                }
                sortedPairs
            }
        val graph = Graph(points)
        var step = 0
        sortedPairs.forEach { pair ->
            if (step == steps) return@forEach
            val (p1, p2) = pair
            graph.addEdge(p1, p2)
            step++
        }
        val subgraphs = TreeSet<Set<Point>>(compareByDescending { it.size }).apply {
            addAll(subgraphs(graph))
        }
        return subgraphs.take(3).fold(1) { result, subgraph ->
            result * subgraph.size
        }
    }

    fun part2(input: List<String>): Int {
        val points = input.map { line ->
            val (x, y, z) = line.split(',').map { it.toInt() }
            Point(x, y, z)
        }
        val sortedPairs =
            points.foldIndexed(TreeSet<PairOfPoints>(compareBy { it.distance() })) { index1, sortedPairs, point1 ->
                points.forEachIndexed { index2, point2 ->
                    if (index1 == index2) return@forEachIndexed
                    val pair = PairOfPoints(point1, point2)
                    if (pair in sortedPairs) return@forEachIndexed
                    sortedPairs.add(pair)
                }
                sortedPairs
            }
        val graph = Graph(points)
        val lastPair = sortedPairs.first { pair ->
            val (p1, p2) = pair
            graph.addEdge(p1, p2)
            subgraph(graph, p1, mutableSetOf()).size == graph.nodes.size
        }
        return lastPair.p1.x * lastPair.p2.x
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day08_test")
    check(part1(testInput, 10) == 40)
    check(part2(testInput) == 25272)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day08")
    part1(input, 1000).println()
    part2(input).println()
}
