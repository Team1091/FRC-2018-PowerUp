package com.team1091.planning

import com.team1091.math.Matrix2d
import com.team1091.math.Rectangle
import com.team1091.math.Vec2
import com.team1091.pathfinding.findPath

fun makePath(startingPos: StartingPos, endingPosition: EndingPos, obstacles: List<Rectangle>): List<Vec2>? {

    val safeDist = 3
    val xSize = 20
    val ySize = 30

    val start = startingPos.pos
    val end = endingPosition.pos

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

    return findPath(fieldMap, start, end)
}