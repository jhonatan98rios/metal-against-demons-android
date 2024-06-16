package com.teixeirarios.mad.lib.utils

object Calculus {

    fun roundToTwoDecimalPlaces(value: Float): Float {
        val factor = 100.0f
        return (value * factor).toInt() / factor
    }
}