package ru.sber.tokenring

fun main() {
    Experiment(
        ringSize = 8,
        queueSize = 2,
        delayMillis = 10,
        load = 1.0
    ).run()
}