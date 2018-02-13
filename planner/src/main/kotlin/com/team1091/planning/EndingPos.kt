package com.team1091.planning

import com.team1091.math.Vec2

enum class EndingPos(val pos: Vec2, val facing: Facing) {

    LEFT_SWITCH(FieldMeasurement.leftSwitchEnd, Facing.RIGHT),
    RIGHT_SWITCH(FieldMeasurement.rightSwitchEnd, Facing.LEFT),

    LEFT_SCALE(FieldMeasurement.leftScaleEnd, Facing.RIGHT),
    RIGHT_SCALE(FieldMeasurement.rightScaleEnd, Facing.LEFT)

}