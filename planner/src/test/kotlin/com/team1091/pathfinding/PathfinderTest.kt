import com.stewsters.pathfinding.*
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

//        obstacles.forEach { println (it.minDist(Vec2[2,10]) )}

        val fieldMap = Matrix2d<Double>(xSize, ySize, { x, y ->

            val d : Double = obstacles.map { it.minDist(Vec2[x, y]) }.min()!!
            if(d <= 0){
                 Double.MAX_VALUE
            } else if(d > safeDist ){
                1.0
            } else {
                2 * (1 - (d / safeDist)) + 1
            }

            //obstacles.find { it.inside(Vec2[x, y]) } == null
        })





        val start = Vec2(1, 1)
        val end = Vec2(16, 5)

        val path = findPath(fieldMap, start, end)

        for (ym in 1..fieldMap.ySize){
            val y = fieldMap.ySize - ym
            for (x in 0..fieldMap.xSize-1){
                val value = Math.min(Math.round(fieldMap[x, y]), 9)

                if (path?.contains(Vec2[x, y])?:false){
                    print("X ")
                } else {
                    print(value.toString() + " ")
                }
            }
            println()
        }

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
}