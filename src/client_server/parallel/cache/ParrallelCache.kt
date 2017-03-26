package client_server.parallel.cache

import java.util.*
import java.util.concurrent.ConcurrentHashMap


/**
 * Class that implements the core of the cache system
 * @author author
 */
class ParallelCache {

    /**
     * Hashmap to store the items of the cache
     */
    private val cache = ConcurrentHashMap<String, CacheItem>()

    /**
     * Task to clean the cache
     */
    private val task = CleanCacheTask(this)

    /**
     * Thread to execute the clean task
     */
    private val thread: Thread

    /**
     * Constructor of the class
     */
    init {
        thread = Thread(task)
        thread.start()
    }

    /**
     * Method that stores an element in the cache
     * @param command Command to store
     * *
     * @param response Response to that command
     */
    fun put(command: String, response: String) {
        val item = CacheItem(command, response)
        cache.put(command, item)
    }

    /**
     * Method that returns an element of the cache or null if it doesn't exists
     * @param command Command to look for in the cache
     * *
     * @return Response of that command
     */
    operator fun get(command: String): String? {
        val item = cache[command] ?: return null
        item.accessDate = Date()
        return item.response
    }

    fun cleanCache() {
        val keys = cache.keys()
        val revisionDate = Date()

        while (keys.hasMoreElements()) {
            val key = keys.nextElement()
            val item = cache[key]!!
            if (revisionDate.time - item.accessDate.time > 600000) {
                cache.remove(key)
            }
        }
    }

    /**
     * Method that shutdown the cache
     */
    fun shutdown() {
        thread.interrupt()
    }

    /**
     * Method that retuns the number of items in the cache
     * @return The number of items in the cache
     */
    val itemNumber: Int
        get() = cache.size

}
