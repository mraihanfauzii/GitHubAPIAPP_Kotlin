package com.bangkit.submissionfundamentalaplikasiandroid.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingViewModel(private val preferences: Setting) : ViewModel() {

    fun getSetting() = preferences.getSetting().asLiveData()

    fun saveSetting(isDarkMode: Boolean) {
        viewModelScope.launch {
            preferences.saveSetting(isDarkMode)
        }
    }

    class Factory(private val preferences: Setting) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(model: Class<T>): T = SettingViewModel(preferences) as T
    }
}