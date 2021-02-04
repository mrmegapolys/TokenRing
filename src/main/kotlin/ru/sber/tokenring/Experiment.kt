package ru.sber.tokenring

import java.lang.System.nanoTime
import java.lang.Thread.sleep

class Experiment {
    private val ringSize: Int = 5

    fun run() {
        val ring = TokenRing(ringSize, 3, 100, generateInitialTokens())
        ring.start()
        sleep(1000)
        ring.stop()
        println(ring.statistics)
    }

    private fun generateInitialTokens() = (0 until ringSize)
        .map {
            listOf(Token(it, (it + 1) % ringSize, nanoTime()))
        }
}

fun main() {
    Experiment().run()
}
