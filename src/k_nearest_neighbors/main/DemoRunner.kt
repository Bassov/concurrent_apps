package k_nearest_neighbors.main

import k_nearest_neighbors.DATA_TEST_PATH
import k_nearest_neighbors.DATA_TRAIN_PATH
import k_nearest_neighbors.loader.BankMarketingLoader
import k_nearest_neighbors.parallel.AbstractKnn
import java.util.*


class DemoRunner(val knn: AbstractKnn, val description: String = "not defined") {
    fun run() {
        val loader = BankMarketingLoader()
        val train = loader.load(DATA_TRAIN_PATH)
        println("Train: " + train.size)
        val test = loader.load(DATA_TEST_PATH)
        println("Test: " + test.size)
        var currentTime: Double
        var success = 0
        var mistakes = 0
        val k = 10
        val classifier = knn
        knn.k = k
        knn.dataSet = train

        val start: Date = Date()
        for (example in test) {
            val tag = classifier.classify(example)
            if (tag == example.tag) {
                success++
            } else {
                mistakes++
            }
        }
        val end: Date = Date()

        currentTime = (end.time - start.time).toDouble()

        classifier.destroy()
        println("******************************************")
        println("$description - K: $k")
        println("Success: $success")
        println("Mistakes: $mistakes")
        println("Execution Time: ${currentTime / 1000} seconds.")
        println("******************************************")

    }
}