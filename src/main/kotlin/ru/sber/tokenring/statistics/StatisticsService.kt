package ru.sber.tokenring.statistics

class StatisticsService {
    private val statistics = mutableListOf<TokenArrival>()

    @Synchronized
    fun addNodeStatistics(nodeStatistics: List<TokenArrival>) {
        statistics += nodeStatistics
    }

    fun getStatistics() =
        statistics.sortedBy(TokenArrival::arrivalTime)
}