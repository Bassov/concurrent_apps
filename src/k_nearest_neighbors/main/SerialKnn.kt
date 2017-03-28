package k_nearest_neighbors.main

import k_nearest_neighbors.serial.KnnClassifier


fun main(args: Array<String>) {
    val classifier = KnnClassifier(emptyList(), 0)
    val description = "Coarse Grained Knn implementation"
    DemoRunner(classifier, description).run()
}