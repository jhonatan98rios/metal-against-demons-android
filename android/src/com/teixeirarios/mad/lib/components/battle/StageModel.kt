package com.teixeirarios.mad.lib.components.battle

import androidx.annotation.DrawableRes
import com.teixeirarios.mad.R

data class StageModel(
    val id: Int,
    val title: String,
    val goal: String,
    @DrawableRes val background: Int,
)

object StageManager {
    private val gameDataMap: MutableMap<Int, StageModel> = mutableMapOf()

    init {
        addStage(StageModel(0, "Highway to Hell", "Kill at least 25 demons", R.drawable.stage_0))
        addStage(StageModel(1, "Welcome to Hell", "Kill at least 50 demons", R.drawable.stage_1))
        addStage(StageModel(2, "Dance with the Devil", "Kill at least 100 demons", R.drawable.stage_2))
        addStage(StageModel(3, "Sympathy for the Devil", "Kill at least 250 demons", R.drawable.stage_3))
        addStage(StageModel(4, "Waking the Demon", "Kill the demon Azazel", R.drawable.stage_4))
        addStage(StageModel(5, "Go to hell for heaven's sake", "Kill at least 500 demons", R.drawable.stage_5))
        addStage(StageModel(6, "The number of the Beast", "Kill at least 666 demons", R.drawable.stage_6))
        addStage(StageModel(7, "Gates of Hell", "Kill at least 750 demons", R.drawable.stage_7))
        addStage(StageModel(8, "Straight to Hell", "Kill at least 1000 demons", R.drawable.stage_8))
        addStage(StageModel(9, "Hell's Bells", "Kill the Twin Gargoyles", R.drawable.stage_9))
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