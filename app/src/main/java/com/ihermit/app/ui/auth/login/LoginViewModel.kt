package com.ihermit.app.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihermit.app.data.repository.AuthRepository
import com.ihermit.app.data.repository.UserRepository
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    sealed class Event {
        object LoggedIn : Event()
        object Registered : Event()
    }

    private val eventChannel = BroadcastChannel<Event>(Channel.CONFLATED)
    val events = eventChannel.asFlow()

    fun auth(email: String, password: String) {
        viewModelScope.launch {
            if (email.isEmpty() || password.isEmpty()) {
                // TODO(malvinstn): Validation email and password and error message.
                return@launch
            }
            _isLoading.value = true
            val newUser = authRepository.auth(email, password)
            userRepository.fetchUser()
            _isLoading.value = false
            if (newUser) {
                eventChannel.offer(Event.Registered)
            } else {
                eventChannel.offer(Event.LoggedIn)
            }
        }
    }
}
