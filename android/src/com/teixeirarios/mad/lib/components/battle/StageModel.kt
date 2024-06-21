package com.teixeirarios.mad.lib.components.battle

import androidx.annotation.DrawableRes
import com.teixeirarios.mad.R

data class StageModel(
    val id: Int,
    val title: String,
    val goal: Int,
    @DrawableRes val background: Int,
)

object StageManager {
    private val gameDataMap: MutableMap<Int, StageModel> = mutableMapOf()

    init {
        addStage(StageModel(0, "Highway to Hell", 25, R.drawable.stage_1))
        addStage(StageModel(1, "Welcome to Hell", 50, R.drawable.stage_2))
        addStage(StageModel(2, "Dance with the Devil", 100, R.drawable.stage_3))
        addStage(StageModel(3, "Sympathy for the Devil", 200, R.drawable.stage_4))
        addStage(StageModel(4, "Waking the Demon", 300, R.drawable.stage_5))
        addStage(StageModel(5, "Hell Awaits", 500, R.drawable.stage_6))
        addStage(StageModel(6, "The number of the Beast", 666, R.drawable.stage_7))
        addStage(StageModel(7, "Gates of Hell", 750, R.drawable.stage_8))
        addStage(StageModel(8, "Straight to Hell", 1000, R.drawable.stage_9))
        addStage(StageModel(9, "Hell's Bells", 1200, R.drawable.stage_10))
        addStage(StageModel(10, "Go to hell for heaven's sake", 1500, R.drawable.stage_11))
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