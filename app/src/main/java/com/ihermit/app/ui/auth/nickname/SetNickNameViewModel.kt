package com.ihermit.app.ui.auth.nickname

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihermit.app.data.repository.UserRepository
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SetNickNameViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    sealed class Event {
        object Updated : Event()
    }

    private val eventChannel = BroadcastChannel<Event>(Channel.CONFLATED)
    val events = eventChannel.asFlow()

    fun updateNickName(nickName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            userRepository.updateNickName(nickName)
            _isLoading.value = false
            eventChannel.offer(Event.Updated)
        }
    }
}
