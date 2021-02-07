package ru.sber.tokenring

fun main() {
    val experiments = (0..1).map { delayMillis ->
        (2..24).map { ringSize ->
            listOf(1, ringSize / 2, ringSize).map { queueSize ->
                Experiment(
                    delayMillis = delayMillis.toLong(),
                    ringSize = ringSize,
                    queueSize = queueSize,
                    experimentLengthSeconds = 30
                )
            }
        }.flatten()
    }.flatten()

    experiments.forEachIndexed { index, experiment ->
        println("Running experiment #$index")
        experiment.run()
    }
}