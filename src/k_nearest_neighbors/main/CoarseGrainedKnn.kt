package k_nearest_neighbors.main

import k_nearest_neighbors.parallel.CoarseGrainedKnn

fun main(args: Array<String>) {
    val classifier = CoarseGrainedKnn(emptyList(), 0)
    val description = "Coarse Grained Knn implementation"
    DemoRunner(classifier, description).run()
}
