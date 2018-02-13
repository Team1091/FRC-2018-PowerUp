package com.team1091.pathfinding

import com.team1091.math.Matrix2d
import com.team1091.math.Matrix3d
import com.team1091.math.Vec3
import com.team1091.planning.Facing
import java.lang.Math.abs

fun findPath3d(field: Matrix2d<Double>, start: Vec3, end: Vec3): List<Vec3>? {

    val directions = Facing.values().size
    val costs = Matrix3d(field.xSize, field.ySize, directions, { _, _, _ -> Double.MAX_VALUE })
    costs[start] = 0.0
    val parent = Matrix3d<Vec3?>(field.xSize, field.ySize, directions, { _, _, _ -> null })

    val openSet = mutableListOf<Vec3>()
    val closeSet = HashSet<Vec3>()

    openSet.add(start)
    while (openSet.isNotEmpty()) {

        // Grab the next node with the lowest cost
        val cheapestNode: Vec3 = openSet.minBy { costs[it] }!!

        if (cheapestNode == end) {
            // target found, we have a path
            val path = mutableListOf(cheapestNode)

            var node = cheapestNode
            while (parent[node] != null) {
                node = parent[node]!!
                path.add(node)
            }
            return path.reversed()
        }

        openSet.remove(cheapestNode)

        // get the neighbors
//        val neighbors = cheapestNode.vonNeumanNeighborhood() // TODO: we probably need to generate neighbors

        val facing = Facing.values()[cheapestNode.z]
        val facings = Facing.values().size
        val neighbors = listOf(
                Vec3[cheapestNode.x + facing.offset.x, cheapestNode.y + facing.offset.y, cheapestNode.z], // forwards
//                Vec3[cheapestNode.x - facing.offset.x, cheapestNode.y - facing.offset.y, cheapestNode.z], // backwards

                // turn to loop
                Vec3[cheapestNode.x, cheapestNode.y, (cheapestNode.z + 1) % facings],
                Vec3[cheapestNode.x, cheapestNode.y, (cheapestNode.z - 1 + facings) % facings]
        )

        //  for each point, set the cost, and a pointer back if we set the cost
        neighbors
                .filter { inside(it, field) } // make sure we are on the field
                .filter { valueSmall(it, field) } // make sure we don't hit switches
                .forEach {
                    var nextCost = costs[cheapestNode] + abs(cheapestNode.z - it.z) * 4

                    nextCost += maxCost(field, it)

                    for (x in -1..1) {
                        for (y in -1..1) {
                            nextCost += field[it.x + x, it.y + y]
                        }
                    }

                    if (nextCost < costs[it]) {
                        costs[it] = nextCost
                        parent[it] = cheapestNode


                        if (closeSet.contains(it)) {
                            closeSet.remove(it)
                        }
                        openSet.add(it)
                    }
                }

        closeSet.add(cheapestNode)
    }

    // could not find a path
    return null

}

fun maxCost(field: Matrix2d<Double>, pos: Vec3): Double {
    var max = 0.0
    for (x in -1..1) {
        for (y in -1..1) {
            val nx = pos.x + x
            val ny = pos.y + y
            max = Math.max(max, field[nx, ny])
        }
    }
    return max
}

private fun valueSmall(pos: Vec3, field: Matrix2d<Double>): Boolean {
    for (x in -1..1) {
        for (y in -1..1) {
            val nx = pos.x + x
            val ny = pos.y + y
            //println("${nx}  ${ny}")

            if (field[nx, ny] > 100000) {
                return false
            }
        }
    }
    return true
}

private fun inside(pos: Vec3, field: Matrix2d<Double>): Boolean {
    for (x in -1..1) {
        for (y in -1..1) {
            val nx = pos.x + x
            val ny = pos.y + y
            //println("${nx}  ${ny}")

            if (field.outside(nx, ny)) {
                return false
            }
        }
    }
    return true
}
