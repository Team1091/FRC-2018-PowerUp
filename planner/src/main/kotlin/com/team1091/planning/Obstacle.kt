package com.team1091.planning

import com.team1091.math.Vec2

interface Obstacle {
    fun minDist(point: Vec2): Double
}