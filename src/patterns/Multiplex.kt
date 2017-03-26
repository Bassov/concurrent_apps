package patterns

import java.util.concurrent.Semaphore


class Multiplex(var count : Int) {
    val semaphore = Semaphore(count)

    fun task() {
        // preCriticalSection()
        semaphore.acquire()
        // criticalSection()
        semaphore.release()
        // postCriticalSection()
    }
}