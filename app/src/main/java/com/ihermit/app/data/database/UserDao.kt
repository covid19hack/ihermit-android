package com.ihermit.app.data.database

import androidx.room.*
import com.ihermit.app.data.entity.User
import com.ihermit.app.data.entity.UserProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query(
        """
            SELECT *
            FROM UserProfile
            WHERE _id = :id
        """
    )
    fun getUser(id: Long): Flow<UserProfile>

    @Query(
        """
            UPDATE UserProfile
            SET nickName = :nickName
            WHERE _id = :id
        """
    )
    suspend fun updateNickName(id: Long, nickName: String)

    @Insert(entity = UserProfile::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)
}
