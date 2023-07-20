package com.bangkit.submissionfundamentalaplikasiandroid.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.submissionfundamentalaplikasiandroid.databinding.ActivityFavoriteBinding
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.submissionfundamentalaplikasiandroid.R
import com.bangkit.submissionfundamentalaplikasiandroid.adapter.UserAdapter
import com.bangkit.submissionfundamentalaplikasiandroid.model.User
import com.bangkit.submissionfundamentalaplikasiandroid.viewmodel.FavoriteViewModel
import com.bangkit.submissionfundamentalaplikasiandroid.room.UserFavorite

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(true)

        supportActionBar?.title = getString(R.string.favorite)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_PROFILE_PICTURE, data.avatar_url)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvUserFavorite.setHasFixedSize(true)
            rvUserFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUserFavorite.adapter = adapter
        }
        viewModel.getUserFavorite()?.observe(this) {
            if (it != null) {
                showLoading(false)
                val list = mapList(it)
                adapter.setListUsers(list)
            }
        }
    }

    private fun mapList(users: List<UserFavorite>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (user in users) {
            val userMap = User(
                user.login,
                user.id,
                user.avatar_url
            )
            listUsers.add(userMap)
        }
        return listUsers
    }

    private fun showLoading(state: Boolean) {
        binding.ProgressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}