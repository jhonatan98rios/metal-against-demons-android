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
        0, 0, 1, 0, 100, 0,
        100, 1f, 1f, 1f, 1f
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
                    userState.speed, userState.luck
                )
            )
        } else {
            updateState(UserStateData(
                0, 0, 1, 0, 100,0,
                1000, 1f, 1f, 1f, 1f
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
        userStateModel.speed = newState.speed
        userStateModel.luck = newState.luck

        UserRepository.getInstance().userState = userStateModel
    }

    fun addMoney(money: Long) {
        val currentState = _state.value
        val updateState = currentState.copy(money = currentState.money + money)
        updateState(updateState)
    }

    fun addExperience(experience: Long) {
        val currentState = _state.value
        var updateState = currentState.copy(experience = currentState.experience + experience)

        if (updateState.experience >= updateState.nextLevelUp) {

            updateState = updateState.copy(
                level = updateState.level + 1,
                experience = updateState.experience - updateState.nextLevelUp,
                nextLevelUp = round(updateState.nextLevelUp * 1.5f).toLong(),
                points = updateState.points + 1
            )
        }

        updateState(updateState)
    }

    fun addHealth() {
        val currentState = _state.value
        if (currentState.points <= 0) return

        val updateState = currentState.copy(
            health =  round(currentState.health * 1.1).toLong(),
            points = currentState.points - 1
        )
        updateState(updateState)
    }

    fun addStrength() {
        val currentState = _state.value
        if (currentState.points <= 0) return

        val updateState = currentState.copy(
            strength = Calculus.roundToTwoDecimalPlaces(currentState.strength * 1.1f),
            points = currentState.points - 1
        )
        updateState(updateState)
    }

    fun addDexterity() {
        val currentState = _state.value
        if (currentState.points <= 0) return

        val updateState = currentState.copy(
            dexterity = Calculus.roundToTwoDecimalPlaces(currentState.dexterity * 1.1f),
            points = currentState.points - 1
        )
        updateState(updateState)
    }

    fun addSpeed() {
        val currentState = _state.value
        if (currentState.points <= 0) return

        val updateState = currentState.copy(
            speed = Calculus.roundToTwoDecimalPlaces(currentState.speed * 1.1f),
            points = currentState.points - 1
        )
        updateState(updateState)
    }

    fun addLuck() {
        val currentState = _state.value
        if (currentState.points <= 0) return

        val updateState = currentState.copy(
            luck = Calculus.roundToTwoDecimalPlaces(currentState.luck * 1.1f),
            points = currentState.points - 1
        )
        updateState(updateState)
    }
}
