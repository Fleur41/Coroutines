package com.sam.coroutines

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    launch(Dispatchers.IO){
        doSomething(1)

    }
    val result: Deferred<String> = async{
        returnSomething()
    }
    println(result.await())
    println("This is printed from main: ${Thread.currentThread().name}")
}

suspend fun doSomething(count: Int){
    println("This is printed from doSomething $count: ${Thread.currentThread().name}")
    doOtherThing(count)
}

suspend fun doOtherThing(count: Int){
    delay(3000)
    println("This is printed from doOtherThing $count: ${Thread.currentThread().name}")

}

suspend fun returnSomething(): String{
    delay(3000)
    return "This is printed from returnSomething: ${Thread.currentThread().name}"

}