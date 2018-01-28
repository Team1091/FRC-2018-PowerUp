package com.stewsters.pathfinding

fun findPath(field: Matrix2d<Double>, start: Vec2, end: Vec2): List<Vec2>? {

    val costs = Matrix2d<Double>(field.xSize, field.ySize, { _, _ -> Double.MAX_VALUE })
    costs[start] = 0.0;
    val parent = Matrix2d<Vec2?>(field.xSize, field.ySize, { _, _ -> null })

    val openSet = mutableListOf<Vec2>()
    val closeSet = HashSet<Vec2>()

    openSet.add(start)
    while (openSet.isNotEmpty()) {

        // Grab the next node with the lowest cost
        val cheapestNode: Vec2 = openSet.minBy { costs[it] }!!

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
        val neighbors = cheapestNode.vonNeumanNeighborhood()

        //  for each point, set the cost, and a pointer back if we set the cost
        neighbors
                .filter { field.contains(it) } // make sure we are on the field
                .filter { field[it] < 100000 } // make sure we don't hit switches
                .forEach {
                    val nextCost = costs[cheapestNode] + field[it]// TODO: use cost on grid

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
