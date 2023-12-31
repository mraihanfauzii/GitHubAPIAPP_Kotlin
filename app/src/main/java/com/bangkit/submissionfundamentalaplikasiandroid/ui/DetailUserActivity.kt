package com.bangkit.submissionfundamentalaplikasiandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bangkit.submissionfundamentalaplikasiandroid.R
import com.bangkit.submissionfundamentalaplikasiandroid.adapter.DetailUserFragmentAdapter
import com.bangkit.submissionfundamentalaplikasiandroid.databinding.ActivityDetailUserBinding
import com.bangkit.submissionfundamentalaplikasiandroid.viewmodel.DetailUserViewModel
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel
    private lateinit var viewPagerAdapter: DetailUserFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_PROFILE_PICTURE)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)
        showLoading(true)

        viewModel = ViewModelProvider(this)[DetailUserViewModel::class.java]
        viewModel.setUserDetail(username.toString())
        viewModel.getUserDetail().observe(this) {
            if (it != null) {
                binding.apply {
                    tvUserName.text = it.name
                    tvUserUsername.text = it.login
                    tvUserFollowers.text = resources.getString(R.string.followers_count, it.followers)
                    tvUserFollowing.text = resources.getString(R.string.following_count, it.following)
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .centerCrop()
                        .into(ivUserProfile)
                    supportActionBar?.title = "${it.login} Profile"
                    showLoading(false)
                }
            }
        }

        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkFavorite(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.toggleFavorite.isChecked = true
                        isChecked = true
                    } else {
                        binding.toggleFavorite.isChecked = false
                        isChecked = false
                    }
                }
            }
        }

        binding.toggleFavorite.setOnClickListener {
            isChecked = !isChecked
            if (isChecked) {
                if (username != null) {
                    if (avatarUrl != null) {
                        viewModel.addToListFavorite(username, id, avatarUrl)
                    }
                }
            } else {
                viewModel.deleteFromFavorite(id)
            }
            binding.toggleFavorite.isChecked = isChecked
        }

        viewPagerAdapter = DetailUserFragmentAdapter(supportFragmentManager, lifecycle, bundle)
        with(binding) {
            ViewPager.adapter = viewPagerAdapter
            TabLayoutMediator(TabLayout, ViewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.followers)
                    1 -> tab.text = getString(R.string.following)
                }
            }.attach()
        }
    }

    private fun showLoading(state: Boolean) {
        binding.ProgressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_PROFILE_PICTURE = "extra_avatar"
    }
}