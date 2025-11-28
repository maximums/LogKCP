package com.maximums

import com.maximums.annotations.LogKCP

@LogKCP
fun myTestFunction() {
    val start = System.currentTimeMillis()
    println("My test println")
    val end = System.currentTimeMillis()
    val result = start - end
    println(result)
}

fun main(args: Array<out String>) {
    myTestFunction()
}