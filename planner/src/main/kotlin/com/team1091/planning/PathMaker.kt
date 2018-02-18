package com.team1091.planning

import com.team1091.math.InverseRectangle
import com.team1091.math.Matrix2d
import com.team1091.math.Vec2
import com.team1091.math.Vec3
import com.team1091.pathfinding.findPath3d
import java.awt.Color
import java.awt.image.BufferedImage

fun makePath(startingPos: StartingPos, endingPos: EndingPos, playerObstacles: List<Obstacle>): List<Vec3>? {

    val fieldMap = createMap(playerObstacles)

    return findPath3d(fieldMap,
            Vec3[startingPos.pos.x, startingPos.pos.y, startingPos.facing.ordinal],
            Vec3[endingPos.pos.x, endingPos.pos.y, endingPos.facing.ordinal])
}

fun createPathImageWithTurns(startPos: StartingPos, obstacles: List<Obstacle>): List<BufferedImage> {

    var result = mutableListOf<BufferedImage>();

    EndingPos.values().forEach { end ->

        //val obstacles = listOf<Obstacle>()//listOf(rightBlock)

        val path = makePath(startPos, end, obstacles)

        if (path == null) {
            println("No possible path, do a default action instead")
        }

        if (end == EndingPos.RIGHT_SWITCH) {
//                    assert(path == null)
        } else {
            assert(path != null)
            assert(path?.first() == Vec3(startPos.pos.x, startPos.pos.y, startPos.facing.ordinal))
            assert(path?.last() == Vec3(end.pos.x, end.pos.y, end.facing.ordinal))
        }
//                println("start ${start} end ${end}")
//                path?.forEach { println("x: ${it.x} y: ${it.y} ${Facing.values()[it.z]}") }

        result.add(drawPathImage(path, obstacles, "${startPos.name}-${end.name}.png"));
    }
    return result;
}


private fun drawPathImage(path: List<Vec3>?, obstacles: List<Obstacle>, name: String): BufferedImage {

    val out = BufferedImage(FieldMeasurement.pathfinderBlocksWidth, FieldMeasurement.pathfinderBlocksLength, BufferedImage.TYPE_INT_RGB)

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

    //val plans = File("build/plans")
    //plans.mkdirs()
    //ImageIO.write(out, "PNG", File(plans, name))
    return out;
}


fun createMap(playerObstacles: List<Obstacle>): Matrix2d<Double> {
    val xSize = FieldMeasurement.pathfinderBlocksWidth
    val ySize = FieldMeasurement.pathfinderBlocksLength
    val safeDist = FieldMeasurement.safeDistance

    val obstacles = mutableListOf<Obstacle>(
            FieldMeasurement.switch,
            FieldMeasurement.scale,
            InverseRectangle(Vec2[0, 0], Vec2[xSize, ySize])
    )
    obstacles.addAll(playerObstacles)

    val fieldMap = Matrix2d<Double>(xSize, ySize, { x, y ->

        val d: Double = obstacles.map { it.minDist(Vec2[x, y]) }.min()!!
        when {
            d <= 0 -> Double.MAX_VALUE
            d > safeDist -> 1.0
            else -> 2 * (1 - (d / safeDist)) + 1
        }
    })
    return fieldMap
}