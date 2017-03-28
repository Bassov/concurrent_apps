package k_nearest_neighbors.parallel

import k_nearest_neighbors.data.Distance
import k_nearest_neighbors.data.Sample
import k_nearest_neighbors.distances.euclideanDist
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

class CoarseGrainedKnn(dataSet: List<Sample>, k: Int) : AbstractKnn(dataSet, k) {
    val cores = Runtime.getRuntime().availableProcessors()
    val pool = Executors.newFixedThreadPool(cores)

    override fun classify(example: Sample): String? {
        val distances = arrayOfNulls<Distance>(dataSet.size)
        val latch = CountDownLatch(cores)

        val length = dataSet.size / cores
        var startIndex = 0
        var endIndex = length

        fun task(start: Int, end: Int) {
            for (i in start until end) distances[i] = Distance(i, euclideanDist(dataSet[i], example))
            latch.countDown()
        }

        for (i in 1..cores) {
            val start = startIndex
            val end = endIndex
            pool.execute { task(start, end) }
            startIndex = endIndex
            endIndex = if (i < cores - 1) endIndex + length else dataSet.size
        }
        latch.await()

        Arrays.parallelSort(distances)
        val results = HashMap<String, Int>()
        (0..k)
                .map { dataSet[distances[it]!!.index].tag }
                .forEach { results.merge(it, 1, { old, new -> old + new }) }
        return results
                .maxBy { entry -> entry.value }
                ?.key
    }

    override fun destroy() {
        pool.shutdown()
    }
}