package k_nearest_neighbors.serial

import k_nearest_neighbors.data.Distance
import k_nearest_neighbors.data.Sample
import k_nearest_neighbors.distances.euclideanDist
import k_nearest_neighbors.parallel.AbstractKnn
import java.util.*

class KnnClassifier(dataSet: List<Sample>, k: Int) : AbstractKnn(dataSet, k) {
    override fun classify(example: Sample): String? {
        val distances = Array(dataSet.size, { i -> Distance(i, euclideanDist(dataSet[i], example)) })
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
}