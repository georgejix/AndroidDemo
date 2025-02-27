package com.jx.normal

import org.junit.Test
import kotlin.math.sqrt

class TestScreen
{
    @Test
    public fun test()
    {
        val density1080 = sqrt(1920.0 * 1920 + 1080 * 1080)  * 25.4 / sqrt(345.0 * 345 + 194 * 194)
        val density720 = sqrt(1280.0 * 1280 + 720 * 720)  * 25.4 / sqrt(345.0 * 345 + 194 * 194)
        val density480 = sqrt(800.0 * 800 + 480 * 480)  * 25.4 / sqrt(345.0 * 345 + 194 * 194)
        println("density1080 = $density1080 density720 = $density720 density480 = $density480")
    }

}
