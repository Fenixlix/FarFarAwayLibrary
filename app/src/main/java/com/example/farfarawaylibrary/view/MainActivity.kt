package com.example.farfarawaylibrary.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.farfarawaylibrary.R
import com.example.farfarawaylibrary.databinding.ActivityMainBinding
import com.example.farfarawaylibrary.model.rvcomponents.CharacterPagingDataAdapter
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

        val pagerAdapter = CharacterPagingDataAdapter(this)
        binding.mainRvCharacters.adapter = pagerAdapter
        binding.mainRvCharacters.layoutManager = LinearLayoutManager(this)

        swCharacterViewModel.allCharacters.observe(this){
            pagerAdapter.submitData(this.lifecycle,it)
        }

        // Use the view model to invoke an alert dialog and then move to other activity to show the data
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