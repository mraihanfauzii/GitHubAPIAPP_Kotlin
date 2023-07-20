package com.bangkit.submissionfundamentalaplikasiandroid.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkit.submissionfundamentalaplikasiandroid.api.RetrofitClient
import com.bangkit.submissionfundamentalaplikasiandroid.model.DetailUser
import com.bangkit.submissionfundamentalaplikasiandroid.room.UserDatabase
import com.bangkit.submissionfundamentalaplikasiandroid.room.UserFavorite
import com.bangkit.submissionfundamentalaplikasiandroid.room.UserFavoriteDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    private var userDao: UserFavoriteDAO?
    private var userDB: UserDatabase?

    val _user = MutableLiveData<DetailUser>()
    private val user: LiveData<DetailUser> = _user

    init {
        userDB = UserDatabase.getDatabase(application)
        userDao = userDB?.userFavoriteDao()
    }

    fun setUserDetail(username: String) {
        RetrofitClient.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUser> {
                override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
                    if (response.isSuccessful) {
                        _user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getUserDetail(): LiveData<DetailUser> {
        return user
    }

    fun addToListFavorite(username: String, id: Int, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = UserFavorite(
                username,
                id,
                avatarUrl
            )
            userDao?.addUserToFavorite(user)
        }
    }

    suspend fun checkFavorite(id: Int) = userDao?.checkFavorite(id)

    fun deleteFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.deleteFromFavorite(id)
        }
    }
}