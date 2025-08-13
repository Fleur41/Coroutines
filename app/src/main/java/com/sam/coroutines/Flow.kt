package com.sam.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.stream.Collectors.toList
import kotlin.time.Duration.Companion.seconds

suspend fun main() {
    val scope = CoroutineScope(Dispatchers.Default)
//    val numbers = getNumbers()
//    numbers
//        .onEach{
//            delay(1.seconds)
//            println("Receiving $it")
//        }

    val numbers = getNumbersFlow().conflate()

    val job1 =scope.launch{
        numbers
            .collect{
            delay((.5).seconds)
            println("Receiver 1 $it")
        }
    }
    val job2 = scope.launch{
        numbers
            .collect{
                delay((.5).seconds)
                println("Receiver 2 $it")
            }
    }
    job1.join()
    job2.join()
//    numbers
//        .onEach {
//            delay((.5).seconds)
//            println("Receiver 1 $it")
//        }
//        .collect{
//            delay((.5).seconds)
//            println("Receiver 1 $it")
//        }



}

suspend fun getNumbers(): List<Int> {
    return (1..50)
        .onEach {
            delay((.5).seconds)
            println("Sending $it")
        }
        .toList()
}

fun getNumbersFlow(): Flow<List<Int>> = flow {
    (1..10)
        .onEach {
            val item = listOf(it, it + 1)
            delay((.5).seconds)
            println("Sending $item")
//            emit(it)
            emit(listOf(it, it + 1))
        }
}