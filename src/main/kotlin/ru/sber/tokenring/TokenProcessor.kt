package ru.sber.tokenring

import java.lang.System.nanoTime
import java.lang.Thread.sleep
import java.util.Random

class TokenProcessor(
    private val id: Int,
    private val delayMillis: Long,
    private val tokenRingSize: Int,
    private val statistics: Statistics
) {
    private val random = Random()

    fun process(token: Token) =
        when (id) {
            token.destinationID -> processAsRecipient(token)
            token.senderId -> processAsSender(token)
            else -> token
        }

    private fun processAsSender(token: Token): Token {
        statistics.handleTokenArrival(nanoTime() - token.sendTime)
        return Token(
            senderId = id,
            destinationID = generateRecipientId(),
            sendTime = nanoTime()
        )
    }

    private fun processAsRecipient(token: Token) =
        token.apply { sleep(delayMillis) }

    private fun generateRecipientId(): Int {
        while (true) {
            random.nextInt(tokenRingSize).run {
                if (this != id) return this
            }
        }
    }
}