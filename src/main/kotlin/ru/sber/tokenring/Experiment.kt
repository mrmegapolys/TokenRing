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
    load: Double,
    private val experimentLengthSeconds: Int
) {
    private val objectMapper = ObjectMapper()
    private val statisticsService = StatisticsService()
    private val initialTokensNum = (load * ringSize).toInt()

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
        val fileName = "experiment_ringSize=$ringSize,tokens=$initialTokensNum," +
                "delay=$delayMillis,queueSize=$queueSize.json"
        objectMapper
            .writerWithDefaultPrettyPrinter()
            .writeValue(File(fileName), statisticsService.getStatistics())
    }

    private fun generateInitialTokens() =
        (0 until initialTokensNum).map {
            listOf(Token(it, (it + 1) % ringSize, nanoTime()))
        } + List(ringSize - initialTokensNum) { emptyList() }
}