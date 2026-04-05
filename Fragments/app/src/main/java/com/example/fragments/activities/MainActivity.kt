package com.example.fragments.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fragments.R
import com.example.fragments.fragments.ListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, ListFragment())
                .commit()
        }
    }
}
