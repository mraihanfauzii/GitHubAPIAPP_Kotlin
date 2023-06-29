package com.bangkit.submissionfundamentalaplikasiandroid.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserFavoriteDAO {
    @Insert
    suspend fun addUserToFavorite(userFavorite: UserFavorite)

    @Query("SELECT * FROM user_favorite")
    fun getUserFavorite(): LiveData<List<UserFavorite>>

    @Query("SELECT count(*) FROM user_favorite WHERE user_favorite.id = :id")
    suspend fun checkFavorite(id: Int): Int

    @Query("DELETE FROM user_favorite WHERE user_favorite.id = :id")
    suspend fun deleteFromFavorite(id: Int): Int
}