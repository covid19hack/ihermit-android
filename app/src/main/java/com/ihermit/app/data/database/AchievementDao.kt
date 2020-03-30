package com.ihermit.app.data.database

import androidx.room.*
import com.ihermit.app.data.entity.Achievement
import kotlinx.coroutines.flow.Flow

@Dao
interface AchievementDao {
    @Query(
        """
            SELECT *
            FROM Achievement
        """
    )
    fun getAllAchievements(): Flow<List<Achievement>>

    @Query(
        """
            SELECT *
            FROM Achievement
            WHERE id = :id
        """
    )
    suspend fun getAchievement(id: Long): Achievement

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(achievements: List<Achievement>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAll(achievements: List<Achievement>)
}
