package k_nearest_neighbors.distances

import k_nearest_neighbors.data.Sample


fun euclideanDist(example1: Sample, example2: Sample): Double {
    var ret = 0.0

    val data1 = example1.example
    val data2 = example2.example

    if (data1.size != data2.size) {
        throw IllegalArgumentException("Vector doesn't have the same length")
    }

    for (i in data1.indices) {
        ret += Math.pow(data1[i] - data2[i], 2.0)
    }
    return Math.sqrt(ret)
}