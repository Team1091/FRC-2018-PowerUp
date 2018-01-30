package com.team1091.planning

import com.team1091.math.Vec2

enum class StartingPos(val pos: Vec2, val facing: Facing) {

    LEFT(Vec2[5, 0], Facing.UP),
    CENTER(Vec2[10, 0], Facing.UP),
    RIGHT(Vec2[15, 0], Facing.UP);

}