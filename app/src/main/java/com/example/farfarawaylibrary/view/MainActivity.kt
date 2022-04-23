package com.example.farfarawaylibrary.view

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.farfarawaylibrary.R
import com.example.farfarawaylibrary.databinding.ActivityMainBinding
import com.example.farfarawaylibrary.model.rvcomponents.RvAdapter
import com.example.farfarawaylibrary.model.rvcomponents.RvItemClick
import com.example.farfarawaylibrary.viewmodel.SwCharacterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() , RvItemClick {

    private lateinit var binding : ActivityMainBinding
    private val swCharacterViewModel : SwCharacterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        swCharacterViewModel.progressBarEstate.observe(this){
            binding.mainProgressBar.isVisible = it
        }

        val rvAdapter = RvAdapter(this)
        binding.mainRvCharacters.adapter = rvAdapter
        binding.mainRvCharacters.layoutManager = LinearLayoutManager(this)

        swCharacterViewModel.onCreate()
        swCharacterViewModel.allCharacters.observe(this){
            rvAdapter.submitList(swCharacterViewModel.allCharacters.value)
        }

        binding.mainFabSearch.setOnClickListener {
            swCharacterViewModel.searchDialog(this, findViewById(R.id.ad_container)) {
                onItemClick(it)
            }
        }
    }

    override fun onItemClick(name: String) {
        val intent = Intent(this, CloseLook::class.java)
        intent.putExtra("name",name)
        startActivity(intent)
    }
}