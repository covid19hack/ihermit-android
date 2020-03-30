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
    private val _breaches = MutableLiveData<Breach?>()
    private val _daily = MutableLiveData<Achievement?>()
    private val _actionItems = MediatorLiveData<List<ActionItem>>().apply {
        addSource(_breaches) { breach: Breach? ->
            if (breach != null) {
                value = listOf(ActionItem.BreachAction(breach))
            }
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
                    _breaches.value = it.lastOrNull()
                }
        }
    }

    fun logout() {
        userPreference.authToken = null
        userPreference.home = null
        userPreference.userId = null
        eventChannel.offer(Event.LoggedOut)
    }

}
