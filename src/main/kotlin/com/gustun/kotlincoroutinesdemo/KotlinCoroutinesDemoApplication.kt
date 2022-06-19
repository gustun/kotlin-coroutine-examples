package com.gustun.kotlincoroutinesdemo

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.springframework.util.StopWatch
import java.text.SimpleDateFormat
import java.util.*


class KotlinCoroutinesDemoApplication

fun main() {
    runBlocking {
        var stopWatch = StopWatch()
        stopWatch.start()

        val deferred1 = async { operation1() }
        val deferred2 = async { operation2() }
        val result = deferred1.await() + deferred2.await()

        stopWatch.stop()
        println(stopWatch.totalTimeMillis)

        stopWatch = StopWatch()
        stopWatch.start()
        val result2 = operation1() + operation2()
        stopWatch.stop()
        println(stopWatch.totalTimeMillis)
    }
}

suspend fun operation1(): Int {
    delay(4000L) // simulated computation
    println("[${(SimpleDateFormat("hh:mm:ss")).format(Date())}] operation1 finished")
    return 50
}

suspend fun operation2(): Int {
    delay(2000L)
    println("[${(SimpleDateFormat("hh:mm:ss")).format(Date())}] operation2 finished")
    return 60
}