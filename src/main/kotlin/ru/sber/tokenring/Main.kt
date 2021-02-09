package ru.sber.tokenring

fun main() {
    val experiments = (2..24).map { ringSize ->
        (1..1).map { queueSize ->
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
    }
}