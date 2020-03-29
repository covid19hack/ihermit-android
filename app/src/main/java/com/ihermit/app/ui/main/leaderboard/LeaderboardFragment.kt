package com.ihermit.app.ui.main.leaderboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ihermit.app.R
import com.ihermit.app.databinding.LeaderboardFragmentBinding

class LeaderboardFragment : Fragment(R.layout.leaderboard_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LeaderboardFragmentBinding.bind(view).setup()
    }

    private fun LeaderboardFragmentBinding.setup() {

    }
}
