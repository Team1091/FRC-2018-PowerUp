package com.team1091.planning

import com.team1091.math.Vec2

enum class EndingPos(val pos: Vec2, val facing: Facing) {

    LEFT_SWITCH(Vec2[5, 10], Facing.RIGHT),
    RIGHT_SWITCH(Vec2[15, 10], Facing.LEFT),

    LEFT_SCALE(Vec2[5, 20], Facing.RIGHT),
    RIGHT_SCALE(Vec2[15, 20], Facing.LEFT)

}