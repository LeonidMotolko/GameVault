package com.example.gamevault.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.gamevault.GameVaultApplication
import com.example.gamevault.R
import com.example.gamevault.data.LibraryRepository
import com.example.gamevault.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: LibraryRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        repository = (application as GameVaultApplication).appContainer.repository

        binding.addButton.setOnClickListener {
            startActivity(Intent(this, EditItemActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        showItems()
    }

    private fun showItems() {
        val items = repository.getItems()
        val titles = items.map { "${it.title}  •  ${it.typeName}" }
        binding.itemsList.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, titles)
        binding.itemsList.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_ID, items[position].id)
            startActivity(intent)
        }
        binding.emptyText.text = if (items.isEmpty()) getString(R.string.empty_list) else ""
    }
}
