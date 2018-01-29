import com.team1091.math.Matrix2d
import com.team1091.math.Rectangle
import com.team1091.math.Vec2
import com.team1091.math.getManhattanDistance
import com.team1091.pathfinding.findPath
import org.junit.Test

class PathfinderTest {

    @Test
    fun testPathfindingInOpen() {

        val safeDist = 3
        val xSize = 20
        val ySize = 30

        // We should take in a list of autonomous "safe zones" for our allies too
        val obstacles = listOf(
                Rectangle(Vec2[5, 5], Vec2[15, 10]), // switch
                Rectangle(Vec2[5, 15], Vec2[15, 16]), // scale

                Rectangle(Vec2[0, ySize], Vec2[xSize, ySize]), // Top of field
                Rectangle(Vec2[xSize, -1], Vec2[xSize, ySize]), //Right of field
                Rectangle(Vec2[-1, -1], Vec2[xSize, -1]), //Bottom of field
                Rectangle(Vec2[-1, -1], Vec2[-1, ySize]) //Left of field
        )

        // This represents the cost to travel as a scalar field.  Keeping ourselves away from the edges
        // is important, since we are not necessarily accurate, and a default path finder will
        // run you as close to the edge as is mathematically possible.
        val fieldMap = Matrix2d<Double>(xSize, ySize, { x, y ->

            val d: Double = obstacles.map { it.minDist(Vec2[x, y]) }.min()!!
            if (d <= 0) {
                Double.MAX_VALUE
            } else if (d > safeDist) {
                1.0
            } else {
                // weight spaces near obstacles higher.
                2 * (1 - (d / safeDist)) + 1
            }
        })

        val start = Vec2(1, 1)
        val end = Vec2(16, 5)

        val path = findPath(fieldMap, start, end)

        printField(fieldMap, path)

        assert(path != null)
        assert(path?.first() == start)
        assert(path?.last() == end)
        assert(path?.size == getManhattanDistance(start, end) + 1)

        path?.forEach { println(it) }
    }


    @Test
    fun testNeighbors() {
        val neighbors = Vec2(3, 3).vonNeumanNeighborhood()

        assert(neighbors.size == 4)
        assert(neighbors.contains(Vec2[3, 4]))
        assert(neighbors.contains(Vec2[3, 2]))
        assert(neighbors.contains(Vec2[2, 3]))
        assert(neighbors.contains(Vec2[4, 3]))
    }

    fun printField(fieldMap: Matrix2d<Double>, path: List<Vec2>? = null) {
        for (ym in 1..fieldMap.ySize) {
            val y = fieldMap.ySize - ym
            for (x in 0..fieldMap.xSize - 1) {
                val value = Math.min(Math.round(fieldMap[x, y]), 9)

                if (path?.contains(Vec2[x, y]) == true) {
                    print("X ")
                } else {
                    print(value.toString() + " ")
                }
            }
            println()
        }

    }
}