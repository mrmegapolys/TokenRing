package ru.sber.tokenring

import ru.sber.tokenring.medium.Medium
import ru.sber.tokenring.statistics.StatisticsService

class Node(
    private val prevMedium: Medium,
    private val nextMedium: Medium,
    private val tokenProcessor: TokenProcessor,
    private val statisticsService: StatisticsService
) {
    private val thread = Thread(::run)

    private fun run() {
        while (true) {
            try {
                val token = prevMedium.get()
                val processedToken = tokenProcessor.process(token)
                nextMedium.put(processedToken)
            } catch (e: InterruptedException) {
                break
            }
        }

        statisticsService.addNodeStatistics(tokenProcessor.statistics)
    }

    fun start(initialTokens: Collection<Token>) {
        initialTokens.forEach(nextMedium::put)
        thread.start()
    }

    fun stop() {
        thread.run {
            interrupt()
            join()
        }
    }
}