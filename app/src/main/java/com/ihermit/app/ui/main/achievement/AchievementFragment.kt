package com.ihermit.app.ui.main.achievement

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.ihermit.app.R
import com.ihermit.app.databinding.AchievementFragmentBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class AchievementFragment : DaggerFragment(R.layout.achievement_fragment) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<AchievementViewModel> { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AchievementFragmentBinding.bind(view).setup()
    }

    private fun AchievementFragmentBinding.setup() {

    }
}
