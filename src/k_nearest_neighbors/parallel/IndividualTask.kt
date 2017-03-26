package k_nearest_neighbors.parallel

import k_nearest_neighbors.data.Distance
import k_nearest_neighbors.data.Sample
import k_nearest_neighbors.distances.euclideanDist
import java.util.concurrent.CountDownLatch


class IndividualTask(
        private val distances: Array<Distance?>,
        private val index: Int,
        private val localExample: Sample,
        private val example: Sample,
        private val endController: CountDownLatch) : Runnable {

    override fun run() {
        distances[index] = Distance(index, euclideanDist(localExample, example))
        endController.countDown()
    }
}