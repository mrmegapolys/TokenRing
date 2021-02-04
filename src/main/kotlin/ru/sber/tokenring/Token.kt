package ru.sber.tokenring

data class Token(
    val senderId: Int,
    val destinationID: Int,
    val sendTime: Long
)