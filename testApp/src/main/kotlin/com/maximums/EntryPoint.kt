package com.maximums

import com.maximums.annotations.LogKCP
import com.maximums.annotations.LogKCPObject

@LogKCP
fun myTestFunction() {
    println("My test println")
}

@LogKCPObject
class Main

fun main(args: Array<out String>) {
    myTestFunction()
    val Loh = Main.MISHA_FILIPESCU_II_CIORT_IBANII
    println("Loh is: ${Loh::class.simpleName}")
}