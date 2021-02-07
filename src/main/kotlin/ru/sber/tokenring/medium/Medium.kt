package ru.sber.tokenring.medium

import ru.sber.tokenring.Token

interface Medium {
    fun put(token: Token)

    fun get(): Token
}