package com.ihermit.app.ui.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ihermit.app.ui.main.achievement.AchievementFragment
import com.ihermit.app.ui.main.leaderboard.LeaderboardFragment

class MainFragmentAdapter(parent: Fragment) : FragmentStateAdapter(parent) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AchievementFragment()
            1 -> LeaderboardFragment()
            else -> throw IllegalStateException("Not supported!")
        }
    }
}
