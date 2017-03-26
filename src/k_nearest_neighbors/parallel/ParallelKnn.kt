package k_nearest_neighbors.parallel

import k_nearest_neighbors.data.Distance
import k_nearest_neighbors.data.Sample
import k_nearest_neighbors.distances.euclideanDist
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors


class ParallelKnn(val dataSet: List<Sample>, val k: Int, val parallelSort: Boolean) {
    val cores = Runtime.getRuntime().availableProcessors()
    val pool = Executors.newFixedThreadPool(cores)

    fun classify(example: Sample): String? {
        val latch = CountDownLatch(dataSet.size)
        val distances = arrayOfNulls<Distance>(dataSet.size)

//        for ((i, sample) in dataSet.withIndex()) pool.submit(IndividualTask(distances, i, sample, example, latch))
        for ((i, sample) in dataSet.withIndex()) pool.submit { task(distances, i, sample, example, latch) }
        latch.await()

        if (parallelSort) Arrays.parallelSort(distances) else distances.sort()
        val results = HashMap<String, Int>()
        (0..k)
                .map { dataSet[distances[it]?.index!!].tag }
                .forEach { results.merge(it, 1, { old, new -> old + new }) }
        return results
                .maxBy { entry -> entry.value }
                ?.key
    }

    fun destroy() {
        pool.shutdown()
    }

}

inline fun task(distances: Array<Distance?>, index: Int, sample: Sample, example: Sample, latch: CountDownLatch) {
    distances[index] = Distance(index, euclideanDist(sample, example))
    latch.countDown()
}