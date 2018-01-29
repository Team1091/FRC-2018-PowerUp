package com.stewsters.spherical

import com.github.sarxos.webcam.Webcam
import com.github.sarxos.webcam.ds.v4l4j.V4l4jDriver
import spark.kotlin.Http
import spark.kotlin.ignite

var exists = false
var avgX: Double = 0.0
var avgY: Double = 0.0

fun main(args: Array<String>) {

    Webcam.setDriver(V4l4jDriver())

    val second = 1000000000
    val webcam = Webcam.getDefault()

    if (!webcam.isOpen) {
        webcam.open()
    }


    val http: Http = ignite()
    http.port(5805)
    http.get("/") {
        "$avgX,$avgY"
    }

    var lastTimeNs = System.nanoTime()
    var frames = 0

    while (true) {
        val currentTime = System.nanoTime()

        if (currentTime - lastTimeNs >= second) {
            println("fps $frames " + if (exists) "x: $avgX y: $avgY" else "Can't see the cube")
            frames = 0
            lastTimeNs += second
        }

        if (webcam.isImageNew) {
            val image = webcam.image
            var xSum = 0
            var ySum = 0
            var yellowPixelCount = 0
            frames++
            for (x in 0..image.width - 1) {
                for (y in 0..image.height - 1) {
                    val rgb = image.getRGB(x, y)

                    val red: Double = (rgb shr 16 and 0xFF).toDouble() / 255.0
                    val green: Double = (rgb shr 8 and 0xFF).toDouble() / 255.0
                    val blue: Double = (rgb and 0xFF).toDouble() / 255.0

                    val yellow = Math.min(red, green) * (1 - blue)
                    if (yellow > 0.35) {
                        xSum += x
                        ySum += y
                        yellowPixelCount++
                    }

                }
            }

            if (yellowPixelCount > 0) {
                avgX = (xSum.toDouble() / yellowPixelCount.toDouble()) / image.width
                avgY = (ySum.toDouble() / yellowPixelCount.toDouble()) / image.height
                exists = true
            } else {
                avgX = 0.5
                avgY = 0.5
                exists = false
            }
        }
    }

}
//
//
//fun transform(input: BufferedImage): BufferedImage {
//    val center = Point(input.width / 2, input.height / 2)
//    val radius = minOf(center.x, center.y)
//
//    val out = BufferedImage(1980, 512, BufferedImage.TYPE_INT_RGB)
//
//    // Loop over each pixel in the new image, read the pixel value from the old one and set it
//    for (x in 0 until out.width) {
//        for (y in 0 until out.height) {
//
//            val xPercent = x.toDouble() / out.width.toDouble()
//            val yPercent = y.toDouble() / out.height.toDouble()
//
//            val xOld = center.x + radius * yPercent * cos((xPercent * Math.PI * 2))
//            val yOld = center.y + radius * yPercent * sin((xPercent * Math.PI * 2))
//
//            val oldColor = input.getRGB(
//                    xOld.toInt(),
//                    yOld.toInt()
//            )
//            out.setRGB(x, y, oldColor)
//        }
//    }
//    return out
//}

