package client_server.parallel.cache

import java.util.*


class CacheItem(val command: String, val response: String) {
    val creationDate = Date()
    var accessDate = Date()
}
