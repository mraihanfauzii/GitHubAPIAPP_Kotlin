package com.bangkit.submissionfundamentalaplikasiandroid.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.bangkit.submissionfundamentalaplikasiandroid.room.UserDatabase
import com.bangkit.submissionfundamentalaplikasiandroid.room.UserFavorite
import com.bangkit.submissionfundamentalaplikasiandroid.room.UserFavoriteDAO

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var userDao: UserFavoriteDAO?
    private var userDB: UserDatabase?

    init {
        userDB = UserDatabase.getDatabase(application)
        userDao = userDB?.userFavoriteDao()
    }

    fun getUserFavorite(): LiveData<List<UserFavorite>>? {
        return userDao?.getUserFavorite()
    }
}