package com.teixeirarios.mad.lib.utils

object Calculus {

    fun sumFloat(value: Float, increment: Float): Float {
        val scaleFactor = 1000
        val scaledValue = (value * scaleFactor).toInt()
        val scaledIncrement = (increment * scaleFactor).toInt()

        val result = scaledValue + scaledIncrement
        return result.toFloat() / scaleFactor
    }
}