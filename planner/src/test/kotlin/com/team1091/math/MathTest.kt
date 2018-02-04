package com.team1091.math

import com.team1091.math.measurement.*
import org.junit.Test

class MathTest {

    @Test
    fun test2dMath() {
        for (x in -4..35) {
            for (y in -4..35) {

                // println("$x $y :" + Vec2[x, y].toString())
                assert("($x, $y)" == Vec2[x, y].toString())
            }
        }
    }


    @Test
    fun test3dMath() {
        for (x in -4..35) {
            for (y in -4..35) {
                for (z in -4..35) {

                    // println("$x, $y, $z :" + Vec3[x, y, z].toString())
                    assert("($x, $y, $z)" == Vec3[x, y, z].toString())
                }
            }
        }
    }


    @Test
    fun test3dMatrix() {

        val matrix = Matrix3d<Vec3>(10, 10, 10, { x, y, z -> Vec3[x, y, z] });

        for (x in 0 until 10) {
            for (y in 0 until 10) {
                for (z in 0 until 10) {

                    // println("$x, $y, $z :" + matrix[x, y, z].toString())
                    assert(matrix[x, y, z] == Vec3[x, y, z])
                }
            }
        }

    }

    @Test
    fun fieldMeasurements() {
        assert(120.yards.toAmericanFootballFields() == 1.0)
        assert(1.americanFootballFields.toYards() == 120.0)

        assert(2.feet.toInches() == 24.0)
        assert(18.inches.toFeet() == 1.5)

        assert((1.0 + 2.0 / 3.0).feet.toInches() == 20.0)

        assert((Math.PI * 2).radians.toDegrees() == 360.0)
        assert(180.degrees.toRadians() == Math.PI)
    }
}