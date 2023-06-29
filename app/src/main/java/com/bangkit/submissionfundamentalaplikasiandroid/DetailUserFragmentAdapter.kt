package com.bangkit.submissionfundamentalaplikasiandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bangkit.submissionfundamentalaplikasiandroid.detailuser.UserFollowersFragment
import com.bangkit.submissionfundamentalaplikasiandroid.detailuser.UserFollowingFragment

class DetailUserFragmentAdapter(fm: FragmentManager, lifecycle: Lifecycle, data: Bundle) :
    FragmentStateAdapter(fm, lifecycle) {

    private var fragmentBundle: Bundle

    init {
        fragmentBundle = data
    }

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        when (position) {
            0 -> fragment = UserFollowersFragment()
            1 -> fragment = UserFollowingFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment
    }
}