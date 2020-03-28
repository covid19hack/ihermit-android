package com.ihermit.app.data.database

import androidx.room.Dao
import androidx.room.Query
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
}
