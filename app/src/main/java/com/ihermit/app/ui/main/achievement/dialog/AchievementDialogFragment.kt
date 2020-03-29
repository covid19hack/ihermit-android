package com.ihermit.app.ui.main.achievement.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.ihermit.app.R
import com.ihermit.app.databinding.AchievementDialogFragmentBinding
import dagger.android.support.DaggerAppCompatDialogFragment
import javax.inject.Inject

class AchievementDialogFragment : DaggerAppCompatDialogFragment() {

    init {
        setStyle(
            STYLE_NORMAL,
            R.style.AppTheme_Dialog
        )
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val args by navArgs<AchievementDialogFragmentArgs>()

    private val viewModel by viewModels<AchievementDialogViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.achievement_dialog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AchievementDialogFragmentBinding.bind(view).setup()
    }

    private fun AchievementDialogFragmentBinding.setup() {
        viewModel.achievement
            .observe(
                viewLifecycleOwner,
                Observer { achievement ->
                    if (achievement != null) {
                        title.text = "Achievement"
                        name.text = achievement.title
                        description.text = achievement.description
                        okBtn.text = "Got it"
                        cancelBtn.text = "Dismiss"
                        cancelBtn.isVisible = !achievement.completed
                    }
                }
            )
        viewModel.fetchAchievement(args.id)
    }
}