package com.bangkit.submissionfundamentalaplikasiandroid.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.bangkit.submissionfundamentalaplikasiandroid.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var viewModel: SettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            SettingViewModel.Factory(Setting(this))
        ).get(SettingViewModel::class.java)

        viewModel.getSetting().observe(this) {
            if (it) {
                binding.changeTheme.text = "Dark Mode"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                binding.changeTheme.text = "Light Mode"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            binding.changeTheme.isChecked = it
        }
        binding.changeTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveSetting(isChecked)
        }
    }
}