package ru.sber.tokenring

import java.util.concurrent.ArrayBlockingQueue

class BlockingQueueMedium(capacity: Int) : Medium {
    private val queue = ArrayBlockingQueue<Token>(capacity)

    override fun put(token: Token) = queue.put(token)

    override fun get(): Token = queue.take()
}