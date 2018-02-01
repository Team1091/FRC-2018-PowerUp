package com.team1091.pathfinding

import com.team1091.math.Matrix2d
import com.team1091.math.Vec2
import com.team1091.planning.Facing

data class Node(val position: Vec2, val facing: Facing) {
    fun forward(): Node = Node(position + facing.offset, facing)
    fun leftTurn() = Node(position,this.facing.left())
    fun rightTurn() = Node(position,this.facing.right())
}

data class Path(val positions: Array<Vec2>, val cost: Double)

fun findPathSplit(fieldMap: Matrix2d<Double>, start: Node, end: Node): List<Vec2>? {
    return pathFollow(fieldMap, start, end, arrayOf(start.position), 0.0)?.positions?.toList()
}

fun  pathFollow(fieldMap: Matrix2d<Double>, start: Node, end: Node, pathSoFar :Array<Vec2>, costSoFar: Double): Path? {

    return arrayOf(
            start.forward(),
            start.leftTurn(),
            start.rightTurn())
            .filter{fieldMap.contains(it.position)}
            .filter{!pathSoFar.contains(it.position)}
            .map {
                pathFollow(
                        fieldMap,
                        it,
                        end,
                        pathSoFar.plus(it.position),
                        costSoFar + 1.0)
            }.minBy { it?.cost!! }

}
