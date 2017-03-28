package k_nearest_neighbors.parallel

import k_nearest_neighbors.data.Sample


abstract class AbstractKnn(var dataSet: List<Sample>, var k: Int) {
    abstract fun classify(example: Sample): String?
    open fun destroy() = Unit
}