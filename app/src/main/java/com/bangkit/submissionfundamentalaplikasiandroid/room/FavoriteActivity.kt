package com.bangkit.submissionfundamentalaplikasiandroid.room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.submissionfundamentalaplikasiandroid.databinding.ActivityFavoriteBinding
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.submissionfundamentalaplikasiandroid.UserAdapter
import com.bangkit.submissionfundamentalaplikasiandroid.detailuser.DetailUserActivity
import com.bangkit.submissionfundamentalaplikasiandroid.model.User

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite"

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_PROFILEPICTURE, data.avatar_url)
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