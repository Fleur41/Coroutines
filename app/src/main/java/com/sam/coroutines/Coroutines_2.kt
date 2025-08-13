package com.sam.coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.Duration.Companion.seconds

suspend fun main() {

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Caught exception: ${throwable.localizedMessage}")
    }
    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob() + exceptionHandler)
//    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob() + exceptionHandler)
//    val parentJob = scope.launch(Dispatchers.Unconfined){
//        val childJob = launch {
//            performAnotherWork()
//        }
//        performWork()
//    }
//
//    val job2 = scope.launch(Dispatchers.Unconfined){
//        waitAndPerformWork()
//    }
//    delay(5.seconds)
//    parentJob.cancel()
//    parentJob.join()
    val job1 = scope.launch {
        performWork()
    }
    val job2 = scope.launch {
            performWorkAndThrowException()
    }

    job1.join()
    job2.join()
}

suspend fun performWork(){
    println("Started working from performWork")
    delay(5.seconds)
    println("This is printed from performWork")
}

suspend fun performWorkAndThrowException(){
    println("Started working from performWorkAndThrowException")
    delay(3.seconds)
    throw UnsupportedOperationException("HaHaHa!")
}

suspend fun performAnotherWork(){
    println("Started working from performAnotherWork")
    delay(8.seconds)
    println("This is printed from performAnotherWork")
}

suspend fun waitAndPerformWork(){
    println("Started working from waitAndPerformWork")
    delay(8.seconds)
    println("This is printed from waitAndPerformWork")
}