package ru.sber.tokenring

class TokenRing(
    private val ringSize: Int,
    private val queueSize: Int,
    private val delayMillis: Long,
    private val initialTokens: List<Collection<Token>>
) {
    lateinit var statistics: Statistics private set
    private lateinit var nodes: Collection<Node>

    fun start() {
        statistics = Statistics()
        val processors = sizeRange()
            .map { TokenProcessor(it, delayMillis, ringSize, statistics) }
        val mediums = sizeRange()
            .map { BlockingQueueMedium(queueSize) }
        nodes = sizeRange()
            .map { index -> Node(mediums[index], mediums[(index + 1) % ringSize], processors[index]) }

        nodes.forEachIndexed { index, node -> node.start(initialTokens[index]) }
    }

    private fun sizeRange() = (0 until ringSize)

    fun stop() {
        nodes.forEach(Node::stop)
    }
}