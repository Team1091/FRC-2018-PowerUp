package com.team1091.planning

import com.team1091.math.*
import com.team1091.pathfinding.findPath3d

fun makePath(startingPos: StartingPos, endingPos: EndingPos, playerObstacles: List<Rectangle>): List<Vec3>? {

    val fieldMap = createMap(playerObstacles)

    return findPath3d(fieldMap,
            Vec3[startingPos.pos.x, startingPos.pos.y, startingPos.facing.ordinal],
            Vec3[endingPos.pos.x, endingPos.pos.y, endingPos.facing.ordinal])
}

 fun createMap(playerObstacles: List<Rectangle>): Matrix2d<Double> {
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
        if (d <= 0) {
            Double.MAX_VALUE
        } else if (d > safeDist) {
            1.0
        } else {
            // weight spaces near obstacles higher.
            2 * (1 - (d / safeDist)) + 1
        }
    })
    return fieldMap
}