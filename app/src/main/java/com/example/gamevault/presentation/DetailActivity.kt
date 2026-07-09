package com.example.gamevault.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        repository = (application as GameVaultApplication).appContainer.repository
        itemId = intent.getLongExtra(EXTRA_ID, -1)

        setupEdgeToEdge()

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

    private fun setupEdgeToEdge() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, 0)
            binding.toolbar.setPadding(0, systemBars.top, 0, 0)
            insets
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
        binding.genreText.text = item.genre
        binding.yearText.text = "Year: ${item.year}"
        binding.ratingText.text = "${item.rating} / 10"
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
