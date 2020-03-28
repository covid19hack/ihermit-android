package com.ihermit.app.ui.login

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
            // TODO(malvinstn): Validation email and password.
            if (email.isEmpty() || password.isEmpty()) {

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
