package ru.sber.tokenring

import ru.sber.tokenring.statistics.TokenArrival
import java.lang.System.nanoTime
import java.lang.Thread.sleep
import java.util.Random

class TokenProcessor(
    private val id: Int,
    private val delayMillis: Long,
    private val tokenRingSize: Int
) {
    private val random = Random()
    val statistics = mutableListOf<TokenArrival>()

    fun process(token: Token) =
        when (id) {
            token.destinationID -> processAsRecipient(token)
            token.senderId -> processAsSender(token)
            else -> token
        }

    private fun processAsSender(token: Token): Token {
        acknowledgeTokenArrival(token)
        return Token(
            senderId = id,
            destinationID = generateRecipientId(),
            sendTime = nanoTime()
        )
    }

    private fun acknowledgeTokenArrival(token: Token) {
        val currentTime = nanoTime()
        statistics.add(
            TokenArrival(
                timestamp = currentTime,
                rTT = currentTime - token.sendTime
            )
        )
    }

    private fun processAsRecipient(token: Token) =
        token.apply {
            if (delayMillis != 0L) sleep(delayMillis)
        }

    private fun generateRecipientId(): Int {
        while (true) {
            random.nextInt(tokenRingSize).run {
                if (this != id) return this
            }
        }
    }
}