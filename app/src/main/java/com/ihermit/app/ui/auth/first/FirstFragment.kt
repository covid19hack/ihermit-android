package com.ihermit.app.ui.auth.first

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ihermit.app.R
import com.ihermit.app.databinding.FirstFragmentBinding

class FirstFragment : Fragment(R.layout.first_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirstFragmentBinding.bind(view).setup()
    }

    private fun FirstFragmentBinding.setup() {
        continueBtn.setOnClickListener {
            findNavController().navigate(FirstFragmentDirections.toLoginFragment())
        }
    }
}
