package cpm.zjc.learn.kotlinlearn

import kotlin.concurrent.thread

fun main(args: Array<String>) {
    var myService = MyService()
    thread { myService.methodA() }
    thread { myService.methodB() }
    println("main end...")
}

class MyService {
    private val lockA = Object()
    private val lockB = Object()
    fun methodA(){
        synchronized(lockB,{
            println("methodA Start")
            Thread.sleep(1000)
            println("start ===> call methodB")
            methodB()
        })
    }

    fun methodB(){
        synchronized(lockA,{
            println("methodB Start")
            Thread.sleep(1000)
            println("start ===> call methodA")
            methodA()
        })
    }
}

