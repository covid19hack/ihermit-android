package com.ihermit.app.ui.main.achievement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihermit.app.data.entity.Achievement
import com.ihermit.app.data.repository.UserRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class AchievementViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _achievements = MutableLiveData<List<Achievement>>()
    val achievements: LiveData<List<Achievement>> = _achievements

    init {
        viewModelScope.launch {
            userRepository.getAchievements()
                .collect { achievements ->
                    _achievements.value = achievements
                }
        }
    }
}
