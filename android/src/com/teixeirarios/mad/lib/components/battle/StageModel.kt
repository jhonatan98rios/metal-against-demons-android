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

        addStage(StageModel(10, "Symphony of Destruction", "Kill at least 1200 demons", R.drawable.stage_10))
        addStage(StageModel(11, "Raining Blood", "Kill at least 1300 demons", R.drawable.stage_11))
        addStage(StageModel(12, "Burn in Hell", "Kill at least 1400 demons", R.drawable.stage_12))
        addStage(StageModel(13, "Hell Awaits", "Kill at least 1500 demons", R.drawable.stage_13))
        addStage(StageModel(14, "Nightmare", "Kill the Nightmare", R.drawable.stage_14))

        addStage(StageModel(15, "Born of Fire", "Kill at least 1600 demons", R.drawable.stage_15))
        addStage(StageModel(16, "Paranoid", "Kill at least 1700 demons", R.drawable.stage_16))
        addStage(StageModel(17, "Beyond the Gates of Hell", "Kill at least 1800 demons", R.drawable.stage_17))
        addStage(StageModel(18, "Hellfire", "Kill at least 2000 demons", R.drawable.stage_18))
        addStage(StageModel(19, "Tormentor ", "Kill the Tormentor", R.drawable.stage_19))
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