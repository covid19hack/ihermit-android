package com.ihermit.app.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ihermit.app.data.entity.User
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

    @Insert(entity = UserProfile::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)
}
