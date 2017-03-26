package k_nearest_neighbors.main

import k_nearest_neighbors.DATA_TEST_PATH
import k_nearest_neighbors.DATA_TRAIN_PATH
import k_nearest_neighbors.loader.BankMarketingLoader
import k_nearest_neighbors.parallel.CoarseGrainedKnn
import java.util.*

fun main(args: Array<String>) {
    val loader = BankMarketingLoader()
    val train = loader.load(DATA_TRAIN_PATH)
    println("Train: " + train.size)
    val test = loader.load(DATA_TEST_PATH)
    println("Test: " + test.size)
    var currentTime = 0.0
    var success = 0
    var mistakes = 0
    val k = 10
    val classifier = CoarseGrainedKnn(
            train, k, true)

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
    println("Parallel Classifier Individual - K: " + k
            + " - Factor 1 - Parallel Sort: true")
    println("Success: " + success)
    println("Mistakes: " + mistakes)
    println("Execution Time: " + currentTime / 1000
            + " seconds.")
    println("******************************************")
}
