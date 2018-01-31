package com.team1091.planning

import com.team1091.math.Vec2

enum class Facing(val offset: Vec2) {

    UP(Vec2[0, 1]),
    RIGHT(Vec2[1, 0]),
    DOWN(Vec2[0, -1]),
    LEFT(Vec2[-1, 0])
}