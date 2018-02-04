package com.team1091.math.measurement


// Measurements
public val Number.americanFootballFields: Length
    get() = Length(this.toDouble() * (120.0 * 12.0 * 3.0))

public val Number.yards: Length
    get() = Length(this.toDouble() * (12.0 * 3))

public val Number.feet: Length
    get() = Length(this.toDouble() * 12.0)

public val Number.inches: Length
    get() = Length(this.toDouble())


public class Length(val distance: Double) {
    fun toAmericanFootballFields() = distance / (120.0 * 12.0 * 3.0)
    fun toYards() = distance / (12.0 * 3.0)
    fun toFeet() = distance / (12.0)
    fun toInches() = distance

    operator fun plus(other: Length) = Length(this.distance + other.distance)
    operator fun minus(other: Length) = Length(this.distance - other.distance)
}
