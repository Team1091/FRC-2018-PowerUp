package com.team1091.planning

import com.team1091.math.measurement.feet
import com.team1091.math.measurement.inches

class FieldMeasurement {

    // Measurement of things on the field, used for pathfinding constants
    companion object {
        val fieldWidth = 30.feet - 6.inches
        val fieldHeight = 20.0.feet

        val robotWidth = 32.inches
        val robotHeight = 32.inches

        val lifterMaxHeight = 7.feet
        val switchHeight = 2.feet
        val scaleHeight = 4.feet

        val pathfinderBlocksWidth = 25
        val pathfinderBlocksLength = 30  // we only go a little beyond half way, other half is off limits
        val safeDistance = 3 // blocks away from obstacles that we should avoid

    }

}

