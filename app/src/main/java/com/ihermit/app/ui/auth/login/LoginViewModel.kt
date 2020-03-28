package com.ihermit.app.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihermit.app.data.repository.AuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

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
            if (authRepository.auth(email, password)) {
                // New user
                eventChannel.offer(Event.Registered)
            } else {
                // Existing user
                eventChannel.offer(Event.LoggedIn)
            }
        }
    }
}
