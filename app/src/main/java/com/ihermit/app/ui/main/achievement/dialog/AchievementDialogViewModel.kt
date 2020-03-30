package com.ihermit.app.ui.main.achievement.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihermit.app.data.entity.Achievement
import com.ihermit.app.data.repository.UserRepository
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AchievementDialogViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    sealed class Event {
        object Completed : Event()
    }

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _achievement = MutableLiveData<Achievement?>()
    val achievement: LiveData<Achievement?> = _achievement

    private val eventChannel = BroadcastChannel<Event>(Channel.CONFLATED)
    val events = eventChannel.asFlow()

    fun fetchAchievement(id: Long) {
        viewModelScope.launch {
            _achievement.value = userRepository.getAchievement(id)
        }
    }

    fun completeAchievement() {
        val achievement = achievement.value ?: return
        viewModelScope.launch {
            if (!achievement.completed && achievement.userCanEdit) {
                _isLoading.value = true
                userRepository.updateAchievement(
                    achievement.copy(
                        progress = achievement.progress + 1
                    )
                )
                _isLoading.value = false
            }
            eventChannel.offer(Event.Completed)
        }
    }
}
