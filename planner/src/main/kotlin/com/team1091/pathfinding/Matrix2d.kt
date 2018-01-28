package com.stewsters.pathfinding

class Matrix2d<T>(val xSize: Int, val ySize: Int, val data: Array<T>) {

    operator fun get(p: Vec2): T = get(p.x, p.y)

    operator fun get(x: Int, y: Int): T {
        return data[x + y * xSize]
    }

    operator fun set(p: Vec2, value: T) = set(p.x, p.y, value)

    operator fun set(x: Int, y: Int, value: T) {
        data[x + y * xSize] = value
    }

    fun contains(p: Vec2): Boolean {
        return contains(p.x, p.y)
    }

    fun contains(x: Int, y: Int): Boolean {
        return !outside(x, y)
    }

    fun outside(x: Int, y: Int): Boolean {
        return (x < 0 || y < 0 || x >= xSize || y >= ySize)
    }
}

inline fun <reified T> Matrix2d(xSize: Int, ySize: Int, init: (Int, Int) -> T) =
        Matrix2d(xSize, ySize, Array(xSize * ySize, { i -> init(i % xSize, i / xSize) }))
