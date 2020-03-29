package com.ihermit.app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    }

    fun logout() {
        userPreference.authToken = null
        userPreference.home = null
        userPreference.userId = null
        eventChannel.offer(Event.LoggedOut)
    }

}
