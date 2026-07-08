package com.example.gamevault.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gamevault.GameVaultApplication
import com.example.gamevault.data.LibraryRepository
import com.example.gamevault.databinding.ActivityDetailBinding
import com.example.gamevault.model.LibraryItem

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var repository: LibraryRepository
    private var itemId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        repository = (application as GameVaultApplication).appContainer.repository
        itemId = intent.getLongExtra(EXTRA_ID, -1)

        binding.editButton.setOnClickListener {
            val intent = Intent(this, EditItemActivity::class.java)
            intent.putExtra(EditItemActivity.EXTRA_ID, itemId)
            startActivity(intent)
        }

        binding.deleteButton.setOnClickListener {
            repository.deleteItem(itemId)
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        showItem()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun showItem() {
        val item = repository.getItem(itemId) ?: run {
            finish()
            return
        }
        binding.titleText.text = item.title
        binding.typeText.text = item.typeName
        binding.genreText.text = "Genre: ${item.genre}"
        binding.yearText.text = "Year: ${item.year}"
        binding.ratingText.text = "Rating: ${item.rating}/10"
        binding.descriptionText.text = item.description
        binding.extraText.text = when (item) {
            is LibraryItem.VideoGame -> "Platform: ${item.platform}"
            is LibraryItem.BoardGame -> "Players: ${item.players}"
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}
