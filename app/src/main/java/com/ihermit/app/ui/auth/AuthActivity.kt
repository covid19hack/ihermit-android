package com.ihermit.app.ui.auth

import android.content.Intent
import android.os.Bundle
import com.ihermit.app.data.preference.UserPreference
import com.ihermit.app.databinding.AuthActivityBinding
import com.ihermit.app.ui.main.MainActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class AuthActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (userPreference.userId != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            val binding = AuthActivityBinding.inflate(layoutInflater)
            setContentView(binding.root)
        }
    }
}
