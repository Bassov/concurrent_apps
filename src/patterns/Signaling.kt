package patterns

import java.util.concurrent.locks.ReentrantLock

// section1() will always run before section2()
class Signaling {
    var lock = ReentrantLock()
    var condition = lock.newCondition()

    fun task1() {
        // section1()
        condition.signal()
    }

    fun task2() {
        condition.await()
        // section2()
    }
}