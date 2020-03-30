package com.ihermit.app.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ihermit.app.data.entity.Breach
import kotlinx.coroutines.flow.Flow

@Dao
interface BreachDao {
    @Query(
        """
            DELETE
            FROM Breach 
        """
    )
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<Breach>)

    @Query(
        """
            SELECT *
            FROM Breach
        """
    )
    fun getAllBreaches(): Flow<List<Breach>>

    @Query(
        """
            SELECT *
            FROM Breach
            WHERE _id = :id
        """
    )
    suspend fun getBreach(id: String): Breach

}
