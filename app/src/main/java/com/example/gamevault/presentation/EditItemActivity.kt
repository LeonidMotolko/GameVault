package com.example.gamevault.presentation

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gamevault.GameVaultApplication
import com.example.gamevault.data.LibraryRepository
import com.example.gamevault.databinding.ActivityEditItemBinding
import com.example.gamevault.model.LibraryItem

class EditItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditItemBinding
    private lateinit var repository: LibraryRepository
    private var itemId: Long = -1

    private val gameTypes = listOf("Video Game", "Board Game")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        repository = (application as GameVaultApplication).appContainer.repository
        itemId = intent.getLongExtra(EXTRA_ID, -1)

        setupEdgeToEdge()

        val typeAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, gameTypes)
        binding.typeSpinner.setAdapter(typeAdapter)

        if (itemId != -1L) {
            supportActionBar?.title = "Edit Item"
            fillForm()
        } else {
            supportActionBar?.title = "Add Item"
        }

        binding.saveButton.setOnClickListener { saveItem() }
    }

    private fun setupEdgeToEdge() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            binding.toolbar.setPadding(0, systemBars.top, 0, 0)
            insets
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun fillForm() {
        val item = repository.getItem(itemId) ?: return
        binding.titleInput.setText(item.title)
        binding.genreInput.setText(item.genre)
        binding.yearInput.setText(item.year.toString())
        binding.ratingInput.setText(item.rating.toString())
        binding.descriptionInput.setText(item.description)
        when (item) {
            is LibraryItem.VideoGame -> {
                binding.typeSpinner.setText(gameTypes[0], false)
                binding.extraInput.setText(item.platform)
            }
            is LibraryItem.BoardGame -> {
                binding.typeSpinner.setText(gameTypes[1], false)
                binding.extraInput.setText(item.players)
            }
        }
        binding.typeSpinner.isEnabled = false
    }

    private fun saveItem() {
        val title = binding.titleInput.text.toString().trim()
        val genre = binding.genreInput.text.toString().trim()
        val yearStr = binding.yearInput.text.toString()
        val ratingStr = binding.ratingInput.text.toString()
        val description = binding.descriptionInput.text.toString().trim()
        val extra = binding.extraInput.text.toString().trim()
        val selectedType = binding.typeSpinner.text.toString()

        var hasError = false

        if (title.isEmpty()) {
            binding.titleLayout.error = "Title is required"
            hasError = true
        } else {
            binding.titleLayout.error = null
        }

        if (genre.isEmpty()) {
            binding.genreLayout.error = "Genre is required"
            hasError = true
        } else {
            binding.genreLayout.error = null
        }

        if (extra.isEmpty()) {
            binding.extraLayout.error = "Platform/Players info is required"
            hasError = true
        } else {
            binding.extraLayout.error = null
        }

        if (hasError) return

        val year = yearStr.toIntOrNull() ?: 0
        val rating = ratingStr.toIntOrNull() ?: 0

        val id = if (itemId == -1L) System.currentTimeMillis() else itemId
        val item = if (selectedType == gameTypes[0]) {
            LibraryItem.VideoGame(id, title, genre, year, rating, description, extra)
        } else {
            LibraryItem.BoardGame(id, title, genre, year, rating, description, extra)
        }

        if (itemId == -1L) repository.addItem(item) else repository.updateItem(item)
        Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show()
        finish()
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}
