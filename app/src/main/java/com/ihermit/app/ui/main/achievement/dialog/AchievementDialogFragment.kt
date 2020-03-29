package com.ihermit.app.ui.main.achievement.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ihermit.app.R
import com.ihermit.app.databinding.AchievementDialogFragmentBinding
import dagger.android.support.DaggerAppCompatDialogFragment

class AchievementDialogFragment : DaggerAppCompatDialogFragment() {

    init {
        setStyle(
            STYLE_NORMAL,
            R.style.AppTheme_Dialog
        )
    }

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

    }
}
