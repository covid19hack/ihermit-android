package com.ihermit.app.ui.main.breach

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihermit.app.data.entity.Breach
import com.ihermit.app.data.repository.UserRepository
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BreachDialogViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    sealed class Event {
        object Completed : Event()
    }

    private val eventChannel = BroadcastChannel<Event>(Channel.CONFLATED)
    val events = eventChannel.asFlow()

    private val _breach = MutableLiveData<Breach?>()
    val breach: LiveData<Breach?> = _breach

    fun getBreach(id: String) {
        viewModelScope.launch {
            _breach.value = userRepository.getBreach(id)
        }
    }

    fun dismissBreach(dismiss: Boolean) {
        viewModelScope.launch {
            val id = breach.value?._id
            if (id != null) {
                userRepository.dismissBreach(id, dismiss)
            }
            eventChannel.offer(Event.Completed)
        }
    }
}
