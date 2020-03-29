package com.ihermit.app.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePadding
import com.ihermit.app.databinding.AuthActivityBinding

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = AuthActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
