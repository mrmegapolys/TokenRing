package ru.sber.tokenring

import ru.sber.tokenring.medium.BlockingQueueMedium
import ru.sber.tokenring.medium.Medium
import ru.sber.tokenring.statistics.StatisticsService

class TokenRing(
    private val ringSize: Int,
    private val queueSize: Int,
    private val delayMillis: Long,
    private val initialTokens: List<Collection<Token>>,
    private val statisticsService: StatisticsService
) {
    private lateinit var nodes: Collection<Node>

    fun start() {
        val processors = generateProcessors()
        val mediums = generateMediums()
        nodes = generateNodes(mediums, processors)

        nodes.forEachIndexed { index, node -> node.start(initialTokens[index]) }
    }

    fun stop() {
        nodes.forEach(Node::stop)
    }

    private fun generateNodes(
        mediums: List<Medium>,
        processors: List<TokenProcessor>
    ) = sizeRange()
        .map { index ->
            Node(
                prevMedium = mediums[index],
                nextMedium = mediums[(index + 1) % ringSize],
                tokenProcessor = processors[index],
                statisticsService = statisticsService
            )
        }

    private fun generateMediums() = sizeRange()
        .map { BlockingQueueMedium(queueSize) }

    private fun generateProcessors() = sizeRange()
        .map { index ->
            TokenProcessor(
                id = index,
                delayMillis = delayMillis,
                tokenRingSize = ringSize
            )
        }

    private fun sizeRange() = (0 until ringSize)
}