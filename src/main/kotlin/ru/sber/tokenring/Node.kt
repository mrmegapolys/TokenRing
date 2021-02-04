package ru.sber.tokenring

class Node(
    private val prevMedium: Medium,
    private val nextMedium: Medium,
    private val tokenProcessor: TokenProcessor,
) {
    private lateinit var thread: Thread

    private fun run() {
        while (true) {
            val token = prevMedium.get()
            val processedToken = tokenProcessor.process(token)
            nextMedium.put(processedToken)
        }
    }

    fun start(initialTokens: Collection<Token>) {
        initialTokens.forEach(nextMedium::put)
        thread = Thread(::run)
        thread.start()
    }

    fun stop() {
        thread.interrupt()
        thread.join()
    }
}
