package com.team1091.planning

import com.team1091.math.Vec2

enum class StartingPos(val pos: Vec2, val facing: Facing) {

    LEFT(FieldMeasurement.leftStart, Facing.UP),
    CENTER(FieldMeasurement.centerStart, Facing.UP),
    RIGHT(FieldMeasurement.rightStart, Facing.UP);
}