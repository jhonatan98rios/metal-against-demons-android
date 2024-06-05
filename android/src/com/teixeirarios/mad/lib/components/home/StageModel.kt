package com.teixeirarios.mad.lib.components.home

import androidx.annotation.DrawableRes
import com.teixeirarios.mad.R

data class StageModel(
    val id: Int,
    val title: String,
    @DrawableRes val background: Int,
)

object StageManager {
    private val gameDataMap: MutableMap<Int, StageModel> = mutableMapOf()

    init {
        addStage(StageModel(0, "Highway to Hell", R.drawable.stage_1))
        addStage(StageModel(1, "Hell's Bells", R.drawable.stage_2))
        addStage(StageModel(2, "The number of the Beast", R.drawable.stage_3))
    }

    fun getStageList(): List<StageModel> {
        return gameDataMap.values.toList()
    }

    fun addStage(gameData: StageModel) {
        gameDataMap[gameData.id] = gameData
    }

    fun getStage(id: Int): StageModel? {
        return gameDataMap[id]
    }
}