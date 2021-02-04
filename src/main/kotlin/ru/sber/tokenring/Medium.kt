package ru.sber.tokenring

interface Medium {
    fun put(token: Token)

    fun get(): Token
}