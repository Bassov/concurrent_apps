package client_server.parallel.cache


import java.util.concurrent.TimeUnit

/**
 * Task that cleans the cache of items that has not been accessed
 * in the last minutes. Implements the Runnable interface. It will be executed
 * as a thread
 * @author author
 */
class CleanCacheTask(private val cache: ParallelCache) : Runnable {

    override fun run() {
        try {
            while (true) {
                TimeUnit.SECONDS.sleep(10)
                cache.cleanCache()
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }

}