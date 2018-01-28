package com.stewsters.pathfinding

import java.lang.Math.abs

class Rectangle(val lower: Vec2, val upper: Vec2) {

    fun inside(point: Vec2): Boolean {
        return point.x >= lower.x && point.y >= lower.y && point.x <= upper.x && point.y <= upper.y
    }

    fun minDist(point: Vec2): Double {
        if (inside(point)) {
            return 0.0;
        } else if (point.x >= lower.x && point.x <= upper.x) {
            // X is centered
            if (point.y > upper.y) {
                return abs(point.y.toDouble() - upper.y.toDouble())
            } else {
                return abs(point.y.toDouble() - lower.y.toDouble())
            }
        } else if (point.y >= lower.y && point.y <= upper.y) {
            // Y is centered
            if (point.x < lower.x) {
                return abs(point.x.toDouble() - lower.x.toDouble())
            } else {
                return abs(point.x.toDouble() - upper.x.toDouble())
            }
        } else if (point.x < lower.x && point.y > upper.y) { // upper left
            return getEuclideanDistance(point, Vec2(lower.x, upper.y))

        } else if (point.x > upper.x && point.y > upper.y) { // upper right
            return getEuclideanDistance(point, Vec2(upper.x, upper.y))

        } else if (point.x > upper.x && point.y < lower.y) { // lower right
            return getEuclideanDistance(point, Vec2(upper.x, lower.y))

        } else if (point.x < lower.x && point.y < lower.y) { // lower left
            return getEuclideanDistance(point, Vec2(lower.x, lower.y))
        } else {
            return -1.0;//throw Exception("That's not good")
        }


    }

}
