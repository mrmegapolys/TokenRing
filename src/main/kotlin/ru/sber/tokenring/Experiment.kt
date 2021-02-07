package ru.sber.tokenring

import com.fasterxml.jackson.databind.ObjectMapper
import ru.sber.tokenring.statistics.StatisticsService
import java.io.File
import java.lang.System.nanoTime
import java.lang.Thread.sleep

class Experiment(
    private val ringSize: Int,
    private val queueSize: Int,
    private val delayMillis: Long,
    private val experimentLengthSeconds: Int
) {
    private val objectMapper = ObjectMapper()
    private val statisticsService = StatisticsService()

    fun run() {
        val ring = TokenRing(
            ringSize = ringSize,
            queueSize = queueSize,
            delayMillis = delayMillis,
            initialTokens = generateInitialTokens(),
            statisticsService = statisticsService
        )

        ring.start()
        sleep(experimentLengthSeconds * 1000L)
        ring.stop()

        saveStatistics()
    }

    private fun saveStatistics() {
        val fileName = "experiment_ringSize=$ringSize," +
                "delay=$delayMillis,queueSize=$queueSize.json"
        objectMapper
            .writerWithDefaultPrettyPrinter()
            .writeValue(File(fileName), statisticsService.getStatistics())
    }

    private fun generateInitialTokens() = (0 until ringSize)
        .map {
            listOf(Token(it, (it + 3) % ringSize, nanoTime()))
        }
}