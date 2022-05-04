package com.example.farfarawaylibrary.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import com.example.farfarawaylibrary.R
import com.example.farfarawaylibrary.databinding.ActivityCloseLookBinding
import com.example.farfarawaylibrary.viewmodel.SwCharacterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CloseLook : AppCompatActivity() {
    private lateinit var binding: ActivityCloseLookBinding
    private val swCharacterViewModel : SwCharacterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCloseLookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        swCharacterViewModel.progressBarEstate.observe(this){
            binding.closeLookProgressBar.isVisible = it
        }

        if (intent.hasExtra("name")){
           swCharacterViewModel.getCharacterFromApi(
                intent.getStringExtra("name")?:"R2")
        }

        swCharacterViewModel.oneCharacter.observe(this){
            binding.closeLookTvCcName.text = it.name
            binding.closeLookTvCcHeight.text = HtmlCompat.fromHtml(getString(R.string.height,it.height),HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.closeLookTvCcMass.text = HtmlCompat.fromHtml(getString(R.string.mass,it.mass),HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.closeLookTvCcHairColor.text = HtmlCompat.fromHtml(getString(R.string.hair_color,it.hair_color), HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.closeLookTvCcSkinColor.text = HtmlCompat.fromHtml(getString(R.string.skin_color,it.skin_color),HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.closeLookTvCcEyeColor.text = HtmlCompat.fromHtml(getString(R.string.eye_color,it.eye_color),HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.closeLookTvCcBirthYear.text = HtmlCompat.fromHtml(getString(R.string.birth_year,it.birth_year),HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.closeLookTvCcGender.text = HtmlCompat.fromHtml(getString(R.string.gender,it.gender),HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.closeLookTvCcHomeWorld.text = HtmlCompat.fromHtml(getString(R.string.home_world,it.homeworld),HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.closeLookTvFilms.text =  HtmlCompat.fromHtml(textViewListMaker("Films",it.films),HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.closeLookTvCcSpecies.text =  HtmlCompat.fromHtml(textViewListMaker("Species",it.species),HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.closeLookTvCcVehicles.text =  HtmlCompat.fromHtml(textViewListMaker("Vehicles",it.vehicles),HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.closeLookTvCcStarships.text =  HtmlCompat.fromHtml(textViewListMaker("Starships",it.starships),HtmlCompat.FROM_HTML_MODE_LEGACY)
        }

        // Use the view model to invoke an alert dialog and then look for the character and refresh the screen
        binding.closeLookFabSearch.setOnClickListener {
            swCharacterViewModel.searchDialog(this, findViewById(R.id.ad_container)){
                swCharacterViewModel.getCharacterFromApi(it)
            }
        }

    }

    // This function creates a list of the different items that are given to it
    private fun textViewListMaker( title : String, list: List<String>?) : String{
        var combinedList = " "
        list?.forEach { combinedList += getString(R.string.enter,it) }
        return getString(R.string.tv_lists,title,combinedList)
    }
}