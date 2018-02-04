package com.team1091.planning

import com.team1091.math.measurement.feet
import com.team1091.math.measurement.inches

class FieldMeasurement {

    // Measurement of things on the field, used for pathfinding constants
    companion object {
        val fieldWidth = 10.feet - 6.inches
        val fieldHeight = 10.0.feet

        val robotWidth = 32.inches
        val robotHeight = 32.inches

        val lifterMaxHeight = 7.feet
        val switchHeight = 2.feet
        val scaleHeight = 4.feet

        val pathfinderBlocksWidth = 20
        val pathfinderBlocksLength = 25
//        val

    }

}

