package com.ihermit.app.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.ihermit.app.R
import com.ihermit.app.data.entity.streakDays
import com.ihermit.app.databinding.MainFragmentBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class MainFragment : DaggerFragment(R.layout.main_fragment) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainFragmentBinding.bind(view).setup()
    }

    private fun MainFragmentBinding.setup() {
        pager.adapter = MainFragmentAdapter(this@MainFragment)
        TabLayoutMediator(tabLayout, pager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tab_achievements)
                1 -> getString(R.string.tab_leaderboard)
                else -> throw IllegalStateException("Unhandled position.")
            }
        }.attach()
        appbarLayout.setOnApplyWindowInsetsListener { v, insets ->
            v.updatePadding(top = insets.systemWindowInsetTop)
            insets.consumeSystemWindowInsets()
        }

        viewModel.user.observe(
            viewLifecycleOwner,
            Observer { userProfile ->
                streakDays.text = resources.getQuantityString(
                    R.plurals.streak_days,
                    userProfile.streakDays,
                    userProfile.streakDays
                )
            }
        )

    }
}
