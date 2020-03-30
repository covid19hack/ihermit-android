package com.ihermit.app.data.repository

import com.ihermit.app.data.database.AchievementDao
import com.ihermit.app.data.database.BreachDao
import com.ihermit.app.data.database.UserDao
import com.ihermit.app.data.entity.Achievement
import com.ihermit.app.data.entity.User
import com.ihermit.app.data.entity.UserProfile
import com.ihermit.app.data.network.HermitService
import com.ihermit.app.data.network.request.CheckInRequest
import com.ihermit.app.data.network.request.UpdateUserRequestBody
import com.ihermit.app.data.preference.UserPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userPreference: UserPreference,
    private val hermitService: HermitService,
    private val userDao: UserDao,
    private val achievementDao: AchievementDao,
    private val breachDao: BreachDao
) {

    private suspend fun updateUser(user: User) {
        userDao.update(user)
        achievementDao.updateAll(user.achievements)
        breachDao.deleteAll()
        breachDao.insertAll(user.breaches)
    }

    suspend fun fetchUser() = withContext(Dispatchers.IO) {
        val userId = userPreference.userId
        if (userId != null) {
            try {
                val user = hermitService.getUser()
                userDao.insert(user)
                achievementDao.insertAll(user.achievements)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    suspend fun updateNickName(nickName: String) = withContext(Dispatchers.IO) {
        val userId = userPreference.userId
        if (userId != null) {
            try {
                hermitService.updateNickName(UpdateUserRequestBody(nickName))
                userDao.updateNickName(userId, nickName)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    fun getUser(): Flow<UserProfile> {
        val userId = userPreference.userId
        return if (userId != null) {
            userDao.getUser(userId)
        } else {
            emptyFlow()
        }
    }

    fun getAchievements(): Flow<List<Achievement>> {
        return if (userPreference.userId != null) {
            achievementDao.getAllAchievements()
        } else {
            emptyFlow()
        }
    }

    suspend fun getAchievement(id: Long): Achievement {
        return achievementDao.getAchievement(id)
    }

    suspend fun checkIn(isAtHome: Boolean) {
        if (userPreference.userId != null) {
            try {
                val user = hermitService.checkIn(CheckInRequest(isAtHome))
                updateUser(user)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    suspend fun updateAchievement(achievement: Achievement) {
        if (userPreference.userId != null) {
            try {
                val user = hermitService.updateAchievement(achievement)
                updateUser(user)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}
