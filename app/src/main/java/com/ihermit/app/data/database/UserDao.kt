package com.ihermit.app.data.database

import androidx.room.Dao
import androidx.room.Query
import com.ihermit.app.data.entity.UserProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query(
        """
            SELECT *
            FROM UserProfile
            WHERE id = :id
        """
    )
    fun getUser(id: Long): Flow<UserProfile>
}
