package com.bangkit.submissionfundamentalaplikasiandroid

import android.util.Log
import androidx.lifecycle.*
import com.bangkit.submissionfundamentalaplikasiandroid.api.RetrofitClient
import com.bangkit.submissionfundamentalaplikasiandroid.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel() : ViewModel() {
    val userListFollowers = MutableLiveData<ArrayList<User>>()
    val userListFollowing = MutableLiveData<ArrayList<User>>()

    fun setListFollowers(username: String) {
        RetrofitClient.apiInstance
            .getUserFollowers(username)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        userListFollowers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getListFollowers(): LiveData<ArrayList<User>> {
        return userListFollowers
    }

    fun setListFollowing(username: String) {
        RetrofitClient.apiInstance
            .getUserFollowing(username)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        userListFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getListFollowing(): LiveData<ArrayList<User>> {
        return userListFollowing
    }
}