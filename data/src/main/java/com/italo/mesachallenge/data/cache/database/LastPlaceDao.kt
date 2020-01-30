package com.italo.mesachallenge.data.cache.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.italo.mesachallenge.data.cache.model.LastPlaceCache

@Dao
interface LastPlaceDao {

    @Query("SELECT * FROM last_place LIMIT 1")
    fun getLastPlace(): LastPlaceCache?

    @Transaction
    fun updateLastPlace(lastPlaceCache: LastPlaceCache) {
        deleteLastPlace()
        insertLastPlace(lastPlaceCache)
    }

    @Insert
    fun insertLastPlace(lastPlaceCache: LastPlaceCache)

    @Query("DELETE FROM last_place")
    fun deleteLastPlace()
}