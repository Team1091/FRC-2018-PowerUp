import com.team1091.math.*
import com.team1091.pathfinding.findPath2d
import com.team1091.planning.*
import org.junit.Test
import java.awt.Color
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.io.File
import javax.imageio.ImageIO

class PathfinderTest {

    @Test
    fun testPathfindingInOpen2d() {

        // If blocks are 6.5 * 6.5 inches
        // xSize = 50
        // ySize = 100

        val safeDist = 3
        val xSize = FieldMeasurement.pathfinderBlocksWidth
        val ySize = FieldMeasurement.pathfinderBlocksLength

        // We should take in a list of autonomous "safe zones" for our allies too
        val obstacles = listOf<Obstacle>(
                Rectangle(Vec2[5, 5], Vec2[15, 10]), // switch
                Rectangle(Vec2[5, 15], Vec2[15, 20]), // scale
                InverseRectangle(Vec2[0, 0], Vec2[xSize, ySize]) // playing field
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

        val start = StartingPos.LEFT.pos
        val end = EndingPos.RIGHT_SWITCH.pos

        val path = findPath2d(fieldMap, start, end)

        //    printField(fieldMap /*, path*/)

        assert(path != null)
        assert(path?.first() == start)
        assert(path?.last() == end)

//        assert(path?.size == getManhattanDistance(start, end) + 1)

        // path?.forEach { println(it) }
    }

    @Test
    fun testPathfindingInOpen3d() {


    }

    @Test
    fun testEnds() {
        val map = createMap(listOf())
        StartingPos.values().forEach { start ->
            println(start.pos)

            start.pos.mooreNeighborhood().forEach {
                assert(map.contains(it))
                assert(map[it] < 10000)
            }

        }
        EndingPos.values().forEach { end ->
            println(end.pos)
            end.pos.mooreNeighborhood().forEach {
                assert(map.contains(it))
                assert(map[it] < 10000)
            }

        }
    }

    @Test
    fun testPathMakerWithTurns() {

        val leftBlock = Rectangle(Vec2[0, 11], Vec2[11, 15])
        val rightBlock = Rectangle(Vec2[20, 11], Vec2[27, 15])

        StartingPos.values().forEach { start ->
            EndingPos.values().forEach { end ->

                val obstacles = listOf(rightBlock)

                val path = makePath(start, end, obstacles)

                if (path == null) {
                    println("No possible path, do a default action instead")
                }

                if (end == EndingPos.RIGHT_SWITCH) {
                    assert(path == null)
                } else {
                    assert(path != null)
                    assert(path?.first() == Vec3(start.pos.x, start.pos.y, start.facing.ordinal))
                    assert(path?.last() == Vec3(end.pos.x, end.pos.y, end.facing.ordinal))
                }
//                println("start ${start} end ${end}")
//                path?.forEach { println("x: ${it.x} y: ${it.y} ${Facing.values()[it.z]}") }

                drawPathImage(path, obstacles, "${start.name}-${end.name}.png")

            }
        }
    }

    private fun drawPathImage(path: List<Vec3>?, obstacles: List<Obstacle>, name: String) {

        val out = BufferedImage(FieldMeasurement.pathfinderBlocksWidth, FieldMeasurement.pathfinderBlocksLength, TYPE_INT_RGB)

        val map = createMap(obstacles)

        for (x in 0 until out.width) {
            for (y in 0 until out.height) {

                val ammt = map[x, y].toFloat()

                if (ammt > 10) {
                    out.setRGB(x, out.height - y - 1, Color.BLACK.rgb)
                } else {
                    out.setRGB(x, out.height - y - 1, Color(1 - ammt / 10f, 1 - ammt / 10f, 1 - ammt / 10f).rgb)
                }


            }
        }

        val colors = listOf(Color.RED, Color.GREEN, Color.MAGENTA, Color.CYAN)
        path?.forEach {
            out.setRGB(it.x, out.height - it.y - 1, colors[it.z].rgb)
        }
//        out.graphics.

        val plans = File("build/plans")
        plans.mkdirs()
        ImageIO.write(out, "PNG", File(plans, name))
    }

//
//    @Test
//    fun testPathMakerMk2WithTurns() {
//
//        StartingPos.values().forEach { start ->
//            EndingPos.values().forEach { end ->
//
//                var path = makePath(start, end, listOf(),true)
//
//                if (path == null) {
//                    println("No possible path, do a default action instead")
//                }
//                println("start ${start} end ${end}")
//                path?.forEach { println("x: ${it.x} y: ${it.y} ${Facing.values()[it.z]}") }
//
//            }
//        }
//
//    }

    @Test
    fun testNeighbors() {
        val neighbors = Vec2[3, 3].vonNeumanNeighborhood()

        assert(neighbors.size == 4)
        assert(neighbors.contains(Vec2[3, 4]))
        assert(neighbors.contains(Vec2[3, 2]))
        assert(neighbors.contains(Vec2[2, 3]))
        assert(neighbors.contains(Vec2[4, 3]))
    }

    private fun printField(fieldMap: Matrix2d<Double>, path: List<Vec2>? = null) {
        for (ym in 1..fieldMap.ySize) {
            val y = fieldMap.ySize - ym
            for (x in 0 until fieldMap.xSize) {
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