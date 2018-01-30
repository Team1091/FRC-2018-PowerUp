package com.team1091.planning

import com.team1091.math.*
import com.team1091.pathfinding.findPath

fun makePath(startingPos: StartingPos, endingPosition: EndingPos, playerObstacles: List<Rectangle>): List<Vec2>? {

    val safeDist = 3
    val xSize = 25
    val ySize = 30 // we only go a little beyond half way

    val start = startingPos.pos
    val end = endingPosition.pos

    val obstacles = mutableListOf<Obstacle>(
            Rectangle(Vec2[5, 5], Vec2[15, 10]), // switch
            Rectangle(Vec2[5, 15], Vec2[15, 20]), // scale
            InverseRectangle(Vec2[0, 0], Vec2[xSize, ySize])
    )
    obstacles.addAll(playerObstacles)

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