package com.example.gamevault.presentation

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gamevault.GameVaultApplication
import com.example.gamevault.data.LibraryRepository
import com.example.gamevault.databinding.ActivityEditItemBinding
import com.example.gamevault.model.LibraryItem

class EditItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditItemBinding
    private lateinit var repository: LibraryRepository
    private var itemId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        repository = (application as GameVaultApplication).appContainer.repository
        itemId = intent.getLongExtra(EXTRA_ID, -1)

        binding.typeSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            listOf("Video Game", "Board Game")
        )

        if (itemId != -1L) {
            supportActionBar?.title = "Edit Item"
            fillForm()
        } else {
            supportActionBar?.title = "Add Item"
        }

        binding.saveButton.setOnClickListener { saveItem() }
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
                binding.typeSpinner.setSelection(0)
                binding.extraInput.setText(item.platform)
            }
            is LibraryItem.BoardGame -> {
                binding.typeSpinner.setSelection(1)
                binding.extraInput.setText(item.players)
            }
        }
        binding.typeSpinner.isEnabled = false
    }

    private fun saveItem() {
        val title = binding.titleInput.text.toString().trim()
        val genre = binding.genreInput.text.toString().trim()
        val year = binding.yearInput.text.toString().toIntOrNull() ?: 0
        val rating = binding.ratingInput.text.toString().toIntOrNull() ?: 0
        val description = binding.descriptionInput.text.toString().trim()
        val extra = binding.extraInput.text.toString().trim()

        if (title.isEmpty() || genre.isEmpty() || description.isEmpty() || extra.isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val id = if (itemId == -1L) System.currentTimeMillis() else itemId
        val item = if (binding.typeSpinner.selectedItemPosition == 0) {
            LibraryItem.VideoGame(id, title, genre, year, rating, description, extra)
        } else {
            LibraryItem.BoardGame(id, title, genre, year, rating, description, extra)
        }

        if (itemId == -1L) repository.addItem(item) else repository.updateItem(item)
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
        finish()
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}
