package com.team1091.planning

import com.team1091.math.measurement.*

class FieldMeasurement {

    // Measurement of things on the field, used for pathfinding constants
    companion object {
        val robotWidth = 38.inches
        val robotHeight = 34.inches
        val robotSize = max(robotHeight, robotWidth)

        val pathfinderBlocksWidth = 25
        val pathfinderBlocksLength = 30  // we only go a little beyond half way, other half is off limits
        val safeDistance = 3 // blocks away from obstacles that we should avoid


        val fieldWidth = 27.feet
        val fieldLength = 30.feet

        val switchLowerX = 7.feet
        val switchLowerY = 11.feet
        val switchUpperX = fieldWidth - switchLowerX
        val switchUpperY = switchLowerY + 4.5.feet


        //       val switch = Rectangle(Vec2[])

        val lifterMaxHeight = 7.feet
        val switchHeight = 2.feet
        val scaleHeight = 4.feet
    }

}

