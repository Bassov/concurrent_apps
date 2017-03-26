package patterns

import java.util.concurrent.locks.ReentrantLock

// section2_2() always after section1_1()
// section1_2() always after section2_1()
class Rendezvous {
    val cond1 = ReentrantLock().newCondition()
    val cond2 = ReentrantLock().newCondition()

    fun task1() {
        // section1_1()
        cond1.signal()
        cond2.await()
        // section1_2()
    }

    fun task2() {
        // section2_1
        cond2.signal()
        cond1.await()
        // section2_2()
    }
}