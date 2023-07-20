package com.bangkit.submissionfundamentalaplikasiandroid.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [UserFavorite::class],
    version = 1
)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userFavoriteDao(): UserFavoriteDAO

    companion object {
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase? {
            if (INSTANCE == null) {
                synchronized(UserDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user_favorite"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}