package com.team1091.planning

import com.team1091.math.*
import com.team1091.pathfinding.findPath3d

fun makePath(startingPos: StartingPos, endingPos: EndingPos, playerObstacles: List<Rectangle>): List<Vec3>? {

    val safeDist = 3
    val xSize = 25
    val ySize = 30 // we only go a little beyond half way

    val obstacles = mutableListOf<Obstacle>(
            Rectangle(Vec2[6, 5], Vec2[15, 10]), // switch
            Rectangle(Vec2[6, 15], Vec2[15, 20]), // scale
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

//    if(alt)
//        return findPathSplit(fieldMap,
//                Node(startingPos.pos, startingPos.facing),
//                Node(endingPos.pos, endingPos.facing)
//        )?.map{Vec3[it.x,it.y,0]}
    return findPath3d(fieldMap,
            Vec3[startingPos.pos.x, startingPos.pos.y, startingPos.facing.ordinal],
            Vec3[endingPos.pos.x, endingPos.pos.y, endingPos.facing.ordinal])
}