package ru.sber.tokenring

import java.lang.System.*
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO

class Statistics {
    private val latencies = mutableListOf<Long>()
    private var throughput = ZERO
    private var startTime: Long = 0

    fun start() {
        startTime = nanoTime()
    }

    fun handleTokenArrival(latency: Long) {
        latencies.add(latency)
        throughput += ONE
    }

    override fun toString(): String {
        return "Statistics(latencies=$latencies, throughput=$throughput, startTime=$startTime)"
    }


}