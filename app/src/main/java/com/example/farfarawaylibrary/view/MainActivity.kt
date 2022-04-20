package com.example.farfarawaylibrary.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.farfarawaylibrary.databinding.ActivityMainBinding
import com.example.farfarawaylibrary.model.rvcomponents.RvAdapter
import com.example.farfarawaylibrary.viewmodel.SwCharacterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val swCharacterViewModel : SwCharacterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        swCharacterViewModel.onCreate()

        val rvAdapter = RvAdapter()

        binding.mainRvCharacters.adapter = rvAdapter
        binding.mainRvCharacters.layoutManager = LinearLayoutManager(this)

        swCharacterViewModel.allCharacters.observe(this){
            rvAdapter.submitList(swCharacterViewModel.allCharacters.value)
        }


    }
}