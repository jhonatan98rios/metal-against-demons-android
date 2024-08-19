package com.teixeirarios.mad.lib.store.userstate


data class UserStateData(
    val id: Long,
    val money: Long,
    val level: Int,
    val experience: Long,
    val nextLevelUp: Long,
    val points: Int,
    val health: Long,
    val strength: Float,
    val dexterity: Float,
    val luck: Float,
    val currentStage: Int,
)