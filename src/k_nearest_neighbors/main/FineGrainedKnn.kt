package k_nearest_neighbors.main

import k_nearest_neighbors.parallel.ParallelKnn

fun main(args: Array<String>) {
    val classifier = ParallelKnn(emptyList(), 0)
    val description = "Fine Grained Knn implementation"
    DemoRunner(classifier, description).run()
}
