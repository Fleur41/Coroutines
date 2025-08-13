package com.sam.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class StateFlowExample(){
    val scope = CoroutineScope(Dispatchers.IO)
    val stateFlow = MutableStateFlow(0)

    init {
        scope.launch {
            delay(5.seconds)
            repeat(5){ index ->
                println("Emitting $index")
                delay(1.seconds)
                stateFlow.value = (index + 1)
            }
        }
    }
}
suspend fun main() {
    val stateFlowExample = StateFlowExample()
    val scope = CoroutineScope(Dispatchers.IO)

    scope.launch{
        println("Starting the collection of stateFlow")
        stateFlowExample.stateFlow.collect{
            println("Received $it")
        }
    }.join()
}