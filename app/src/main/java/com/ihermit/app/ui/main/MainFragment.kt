package com.ihermit.app.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.ihermit.app.MainNavDirections
import com.ihermit.app.R
import com.ihermit.app.databinding.MainFragmentBinding
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
        toolbar.inflateMenu(R.menu.main)
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.logout -> {
                    viewModel.logout()
                    true
                }
                else -> throw IllegalStateException("Unhandled")
            }
        }

        viewLifecycleOwner.lifecycleScope
            .launch {
                viewModel.events
                    .collect { event ->
                        when (event) {
                            MainViewModel.Event.LoggedOut -> {
                                findNavController().navigate(MainFragmentDirections.toAuthActivity())
                                requireActivity().finish()
                            }
                        }
                    }
            }

        viewModel.user.observe(
            viewLifecycleOwner,
            Observer { userProfile ->
                if (userProfile != null) {
                    nickName.text = userProfile.nickName
                    userProgress.text = getString(
                        R.string.user_progress,
                        userProfile.levelNumber,
                        userProfile.levelName
                    )
                    streakDays.text = resources.getQuantityString(
                        R.plurals.streak_days,
                        userProfile.streakLength,
                        userProfile.streakLength
                    )
                }
            }
        )

        viewModel.actionItems
            .observe(viewLifecycleOwner,
                Observer { items ->
                    updateActionItems(items)
                }
            )

    }

    private fun MainFragmentBinding.updateActionItems(items: List<ActionItem>) {
        actions.withModels {
            items.forEach { item ->
                actionItem {
                    when (item) {
                        is ActionItem.BreachAction -> {
                            id(item.breach._id)
                        }
                        is ActionItem.NonCompletedAchievement -> {
                            id(item.achievement.id)
                        }
                    }
                    actionItem(item)
                    clickListener { model, _, _, _ ->
                        when (val item = model.actionItem()) {
                            is ActionItem.BreachAction -> {
                                findNavController().navigate(
                                    MainNavDirections.toBreachDialogFragment(
                                        item.breach._id
                                    )
                                )
                            }
                            is ActionItem.NonCompletedAchievement -> {
                                findNavController().navigate(
                                    MainNavDirections.toAchievementDialogFragment(
                                        item.achievement.id
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
