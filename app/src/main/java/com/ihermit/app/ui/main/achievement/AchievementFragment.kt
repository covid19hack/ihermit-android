package com.ihermit.app.ui.main.achievement

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ihermit.app.R
import com.ihermit.app.databinding.AchievementFragmentBinding

class AchievementFragment : Fragment(R.layout.achievement_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AchievementFragmentBinding.bind(view).setup()
    }

    private fun AchievementFragmentBinding.setup() {
        
    }
}
