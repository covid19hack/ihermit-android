package com.ihermit.app.ui.main.achievement.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihermit.app.data.entity.Achievement
import com.ihermit.app.data.repository.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AchievementDialogViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _achievement = MutableLiveData<Achievement?>()
    val achievement: LiveData<Achievement?> = _achievement

    fun fetchAchievement(id: Long) {
        viewModelScope.launch {
            _achievement.value = userRepository.getAchievement(id)
        }
    }
}
