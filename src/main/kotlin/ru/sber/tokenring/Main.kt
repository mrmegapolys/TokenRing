package ru.sber.tokenring

import java.lang.Thread.sleep

fun main() {
    val experiments = listOf(6, 12).map { ringSize ->
        (1..ringSize).map { queueSize ->
            listOf(0.25, 0.5, 0.75, 1.0).map { load ->
                Experiment(
                    ringSize = ringSize,
                    queueSize = queueSize,
                    delayMillis = 0L,
                    load = load,
                    experimentLengthSeconds = 20
                )
            }
        }.flatten()
    }.flatten()

    experiments.forEachIndexed { index, experiment ->
        println("Running experiment #$index")
        experiment.run()
        sleep(30 * 1000)
    }
}