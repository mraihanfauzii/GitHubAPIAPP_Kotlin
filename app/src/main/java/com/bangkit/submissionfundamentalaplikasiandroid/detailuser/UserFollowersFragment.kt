package com.bangkit.submissionfundamentalaplikasiandroid.detailuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.submissionfundamentalaplikasiandroid.FollowViewModel
import com.bangkit.submissionfundamentalaplikasiandroid.R
import com.bangkit.submissionfundamentalaplikasiandroid.UserAdapter
import com.bangkit.submissionfundamentalaplikasiandroid.databinding.FragmentUserFollowersBinding

class UserFollowersFragment : Fragment(R.layout.fragment_user_followers) {
    private lateinit var _binding: FragmentUserFollowersBinding
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowViewModel
    private lateinit var userAdapter: UserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()

        _binding = FragmentUserFollowersBinding.bind(view)

        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()

        binding.apply {
            rvUserFollowers.setHasFixedSize(true)
            rvUserFollowers.layoutManager = LinearLayoutManager(activity)
            rvUserFollowers.adapter = userAdapter
        }
        showLoading(true)
        binding.DataKosong.visibility = View.GONE

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowViewModel::class.java
        )
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner) {
            if (it != null) {
                userAdapter.setListUsers(it)
                if (userAdapter.itemCount < 1) {
                    binding.DataKosong.visibility = View.VISIBLE
                }
                showLoading(false)
            }
        }
    }

    private fun showLoading(state: Boolean) {
        binding.ProgressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}