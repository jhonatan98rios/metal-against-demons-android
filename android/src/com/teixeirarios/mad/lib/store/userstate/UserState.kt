package com.teixeirarios.mad.lib.store.userstate

import android.content.Context
import com.teixeirarios.mad.lib.infra.database.models.UserState as UserStateModel
import com.teixeirarios.mad.lib.infra.database.repository.AppContext
import com.teixeirarios.mad.lib.infra.database.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object UserState {
    private val _state = MutableStateFlow(UserStateData(0, 0))
    val state: StateFlow<UserStateData> get() = _state
    private lateinit var context: AppContext;

    fun init(context: AppContext) {
        this.context = context
        val userState = UserRepository.getInstance(context).userState

        if (userState != null) {
            updateState(
                UserStateData(
                    userState.id,
                    userState.money
                )
            )
        } else {
            updateState(UserStateData(0, 0))
        }
    }

    private fun updateState(newState: UserStateData) {
        _state.value = newState
        val userStateModel = UserStateModel()

        userStateModel.id = newState.id
        userStateModel.money = newState.money
        UserRepository.getInstance().userState = userStateModel
    }

    fun addMoney(money: Long) {
        val newUserState = UserStateData(0, _state.value.money + money)
        updateState(newUserState)
    }
}