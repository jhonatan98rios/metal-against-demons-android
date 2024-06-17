package com.teixeirarios.mad.lib.store.userstate

import com.teixeirarios.mad.lib.infra.database.models.UserState as UserStateModel
import com.teixeirarios.mad.lib.infra.database.repository.AppContext
import com.teixeirarios.mad.lib.infra.database.repository.UserRepository
import com.teixeirarios.mad.lib.utils.Calculus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.round

object UserState {
    private val _state = MutableStateFlow(UserStateData(
        0, 0, 1, 0, 50, 0,
        100, 1f, 1f, 1f, 0
    ))

    val state: StateFlow<UserStateData> get() = _state
    private lateinit var context: AppContext;

    fun init(context: AppContext) {
        this.context = context

        // Update de state with database data
        val userState = UserRepository.getInstance(context).userState

        if (userState != null) {
            updateState(
                UserStateData(
                    userState.id, userState.money, userState.level,
                    userState.experience, userState.nextLevelUp, userState.points,
                    userState.health, userState.strength, userState.dexterity,
                    userState.luck, userState.currentStage
                )
            )
        } else {
            updateState(UserStateData(
                0, 0, 1, 0, 50,0,
                1000, 1f, 1f, 1f, 0
            ))
        }
    }

    private fun updateState(newState: UserStateData) {
        _state.value = newState
        val userStateModel = UserStateModel()

        userStateModel.id = newState.id
        userStateModel.money = newState.money
        userStateModel.level = newState.level
        userStateModel.experience = newState.experience
        userStateModel.nextLevelUp = newState.nextLevelUp
        userStateModel.points = newState.points
        userStateModel.health = newState.health
        userStateModel.strength = newState.strength
        userStateModel.dexterity = newState.dexterity
        userStateModel.luck = newState.luck
        userStateModel.currentStage = newState.currentStage

        UserRepository.getInstance().userState = userStateModel
    }

    fun addMoney(money: Long) {
        val currentState = _state.value
        val updateState = currentState.copy(money = currentState.money + money)
        updateState(updateState)
    }

    fun addHealth() {
        val currentState = _state.value
        if (currentState.points <= 0) return

        val updateState = currentState.copy(
            health = currentState.health + 200,
            points = currentState.points - 1
        )
        updateState(updateState)
    }

    fun addStrength() {
        val currentState = _state.value
        if (currentState.points <= 0) return

        val updateState = currentState.copy(
            strength = Calculus.roundToTwoDecimalPlaces(currentState.strength + 0.2f),
            points = currentState.points - 1
        )
        updateState(updateState)
    }

    fun addDexterity() {
        val currentState = _state.value
        if (currentState.points <= 0 || currentState.dexterity >= 3) return

        val updateState = currentState.copy(
            dexterity = Calculus.roundToTwoDecimalPlaces(currentState.dexterity + 0.1f),
            points = currentState.points - 1
        )
        updateState(updateState)
    }

    fun addLuck() {
        val currentState = _state.value
        if (currentState.points <= 0 || currentState.luck >= 3) return

        val updateState = currentState.copy(
            luck = Calculus.roundToTwoDecimalPlaces(currentState.luck + 0.1f),
            points = currentState.points - 1
        )
        updateState(updateState)
    }
}
