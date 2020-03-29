package com.ihermit.app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihermit.app.data.entity.UserProfile
import com.ihermit.app.data.repository.UserRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _user = MutableLiveData<UserProfile>()
    val user: LiveData<UserProfile> = _user

    init {
        viewModelScope.launch {
            userRepository.getUser()
                .collect { userProfile ->
                    _user.value = userProfile
                }
        }
    }

}
