package com.ihermit.app.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ihermit.app.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(MainActivityBinding.inflate(layoutInflater).root)
    }
}
