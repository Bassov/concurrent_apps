package k_nearest_neighbors.parallel

import k_nearest_neighbors.data.Distance
import k_nearest_neighbors.data.Sample
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors


class ParallelKnn(dataSet: List<Sample>, k: Int) : AbstractKnn(dataSet, k) {
    val cores = Runtime.getRuntime().availableProcessors()
    val pool = Executors.newFixedThreadPool(cores)

    override fun classify(example: Sample): String? {
        val latch = CountDownLatch(dataSet.size)
        val distances = arrayOfNulls<Distance>(dataSet.size)

        for ((i, sample) in dataSet.withIndex()) pool.submit(IndividualTask(distances, i, sample, example, latch))
        latch.await()

        Arrays.parallelSort(distances)
        val results = HashMap<String, Int>()
        (0..k)
                .map { dataSet[distances[it]?.index!!].tag }
                .forEach { results.merge(it, 1, { old, new -> old + new }) }
        return results
                .maxBy { entry -> entry.value }
                ?.key
    }

    override fun destroy() {
        pool.shutdown()
    }

}
