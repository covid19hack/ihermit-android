package com.ihermit.app.ui.main.breach

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.ihermit.app.R
import com.ihermit.app.databinding.ReusableDialogFragmentBinding
import dagger.android.support.DaggerAppCompatDialogFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject

class BreachDialogFragment : DaggerAppCompatDialogFragment() {

    init {
        setStyle(
            STYLE_NORMAL,
            R.style.AppTheme_Dialog
        )
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<BreachDialogViewModel> { viewModelFactory }

    private val args by navArgs<BreachDialogFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.reusable_dialog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ReusableDialogFragmentBinding.bind(view).setup()
    }

    private fun ReusableDialogFragmentBinding.setup() {
        val format = SimpleDateFormat("MM-dd HH:mm", LocaleListCompat.getDefault()[0])
        viewModel.breach.observe(viewLifecycleOwner,
            Observer { breach ->
                if (breach != null) {
                    badge.setImageResource(R.drawable.image_breach)
                    okBtn.text = getString(R.string.breach_dialog_ok_btn_title)
                    cancelBtn.text = getString(R.string.breach_dialog_cancel_btn_title)
                    name.text = getString(R.string.breach_dialog_name)
                    description.text = getString(
                        R.string.breach_dialog_description, format.format(breach.createdAt)
                    )
                    title.text = getString(R.string.breach_dialog_title)
                }
            })
        viewLifecycleOwner
            .lifecycleScope
            .launch {
                viewModel.events
                    .collect { event ->
                        when (event) {
                            BreachDialogViewModel.Event.Completed -> dismiss()
                        }
                    }
            }
        viewModel.isLoading.observe(viewLifecycleOwner,
            Observer { isLoading ->
                okBtn.isEnabled = !isLoading
                cancelBtn.isEnabled = !isLoading
            })
        viewModel.getBreach(args.id)
        okBtn.setOnClickListener {
            viewModel.dismissBreach(true)
        }
        cancelBtn.setOnClickListener {
            viewModel.dismissBreach(false)
        }
    }
}
