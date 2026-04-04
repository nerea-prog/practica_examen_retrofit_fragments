package com.example.fragments.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fragments.R
import com.example.fragments.databinding.ActivityMainBinding
import com.example.fragments.fragments.ListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cargar ListFragment SOLO la primera vez
        // savedInstanceState != null -> la Activity se esta recreando (ej. rotacion)
        // En ese caso el FragmentManager ya restaura el fragmento automáticamente.
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, ListFragment())
                .commit()
        }
    }
}
