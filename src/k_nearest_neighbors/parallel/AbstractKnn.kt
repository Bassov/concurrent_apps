package k_nearest_neighbors.parallel

import k_nearest_neighbors.data.Sample


abstract class AbstractKnn(val dataSet: List<Sample>, val k: Int) {
    abstract fun classify(example: Sample): String?
    open fun destroy() = Unit
}