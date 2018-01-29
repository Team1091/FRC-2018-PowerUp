package com.team1091.math

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
}