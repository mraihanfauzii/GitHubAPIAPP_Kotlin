package com.bangkit.submissionfundamentalaplikasiandroid

import android.util.Log
import androidx.lifecycle.*
import com.bangkit.submissionfundamentalaplikasiandroid.api.RetrofitClient
import com.bangkit.submissionfundamentalaplikasiandroid.model.User
import com.bangkit.submissionfundamentalaplikasiandroid.model.UserResponse
import com.bangkit.submissionfundamentalaplikasiandroid.setting.Setting
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubViewModel(private val preferences: Setting) : ViewModel() {
    val listUsers = MutableLiveData<ArrayList<User>>()

    fun setSearchUser(query: String) {
        RetrofitClient.apiInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        listUsers.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getSearhUser(): LiveData<ArrayList<User>> {
        return listUsers
    }

    fun getSetting() = preferences.getSetting().asLiveData()

    class Factory(private val preferences: Setting) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            GithubViewModel(preferences) as T
    }
}