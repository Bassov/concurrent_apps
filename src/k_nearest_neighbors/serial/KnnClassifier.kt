package k_nearest_neighbors.serial

import k_nearest_neighbors.data.Distance
import k_nearest_neighbors.data.Sample
import java.util.*

class KnnClassifier(var dataSet: List<Sample>, val k: Int) {
    fun classify(example: Sample): String? {
        val distances = Array(dataSet.size, { i -> Distance(i, distance(dataSet[i], example)) })
        distances.sort()

        val results = HashMap<String, Int>()
        (0..k)
                .map { dataSet[distances[it].index] }
                .map { it.tag }
                .forEach { results.merge(it, 1, { old, new -> old + new }) }
        return results
                .maxBy { entry -> entry.value }
                ?.key
    }

    private fun distance(example1: Sample, example2: Sample): Double {
        val data1 = example1.example
        val data2 = example2.example

        if (data1.size != data2.size) {
            throw IllegalArgumentException("Vector doesn't have the same length")
        }

        val ret = data1.indices.sumByDouble { Math.pow(data1[it] - data2[it], 2.0) }
        return Math.sqrt(ret)
    }
}