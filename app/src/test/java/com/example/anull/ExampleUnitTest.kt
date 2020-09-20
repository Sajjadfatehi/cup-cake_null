package com.example.anull

import org.junTest

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun main() {
        println("bib")
        //below code is for when we need to use from job . it possible by start=LAZY
//        val jobi= CoroutineScope(Dispatchers.IO).launch(start = CoroutineStart.LAZY){
//
//        }

//
//        val job1 = GlobalScope.launch(start = CoroutineStart.LAZY) {
//            delay(200)
//            println("Pong2")
//            delay(200)
//        }
//        GlobalScope.launch() {
//            delay(200)
//            println("Ping1")
//            job1.join()
//            println("Ping3")
//            delay(200)
//        }
//        Thread.sleep(1000)

//
//
//        with(GlobalScope) {
//            val parentJob = launch() {
//                delay(200)
//                println("I'm the parent")
//                delay(200)
//            }
//            launch(context = parentJob) {
//                delay(200)
//                println("I'm a child")
//                delay(200)
//            }
//            if (parentJob.children.iterator().hasNext()) {
//                println("The Job has children ${parentJob.children}")
//            } else {
//                println("The Job has NO children")
//            }
//            Thread.sleep(1000)
//        }
//        GlobalScope.launch {
//            val g=call1()
//            val b=call12()
//
//            println(g)
//            println(b)
//
//
//        }
//    }
//    suspend fun call1():String{
//        delay(3000L)
//        return "test 1"
//    }
//      suspend fun call12():String{
//          delay(3000L)
//        return "test 2"
//    }
    }

}