package com.ihermit.app.ui.main

import androidx.lifecycle.*
import com.ihermit.app.data.entity.Achievement
import com.ihermit.app.data.entity.Breach
import com.ihermit.app.data.entity.UserProfile
import com.ihermit.app.data.preference.UserPreference
import com.ihermit.app.data.repository.UserRepository
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userPreference: UserPreference
) : ViewModel() {
    sealed class Event {
        object LoggedOut : Event()
    }

    private val _user = MutableLiveData<UserProfile?>()
    val user: LiveData<UserProfile?> = _user
    private val _breach = MutableLiveData<Breach?>()
    private val _daily = MutableLiveData<Achievement?>()
    private val _actionItems = MediatorLiveData<List<ActionItem>>().apply {
        addSource(_breach) { breach: Breach? ->
            val list: MutableList<ActionItem> = if (breach != null) {
                mutableListOf(ActionItem.BreachAction(breach))
            } else {
                mutableListOf()
            }
            val daily = _daily.value
            if (daily != null) {
                list += ActionItem.NonCompletedAchievement(daily)
            }
            value = list
        }
        addSource(_daily) { achievement: Achievement? ->
            val list: MutableList<ActionItem> = if (achievement != null) {
                mutableListOf(ActionItem.NonCompletedAchievement(achievement))
            } else {
                mutableListOf()
            }
            val breach = _breach.value
            if (breach != null) {
                list += ActionItem.BreachAction(breach)
            }
            value = list
        }
    }
    val actionItems: LiveData<List<ActionItem>> = _actionItems

    private val eventChannel = BroadcastChannel<Event>(Channel.CONFLATED)
    val events = eventChannel.asFlow()

    init {
        viewModelScope.launch {
            userRepository.getUser()
                .collect { userProfile ->
                    _user.value = userProfile
                }
        }
        viewModelScope.launch {
            userRepository.fetchUser()
        }
        viewModelScope.launch {
            userRepository.getAllBreaches()
                .collect {
                    _breach.value = it.lastOrNull()
                }
        }
        viewModelScope.launch {
            userRepository.getAchievements()
                .map { achievements -> achievements.filter { !it.completed } }
                .collect { nonCompleted ->
                    if (nonCompleted.isNotEmpty()) {
                        _daily.value = nonCompleted.random()
                    }
                }

        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.clear()
            eventChannel.offer(Event.LoggedOut)
        }
    }

}
