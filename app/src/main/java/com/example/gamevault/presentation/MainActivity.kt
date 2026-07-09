package com.example.gamevault.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamevault.GameVaultApplication
import com.example.gamevault.R
import com.example.gamevault.data.LibraryRepository
import com.example.gamevault.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: LibraryRepository
    private lateinit var adapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        repository = (application as GameVaultApplication).appContainer.repository

        setupRecyclerView()

        binding.addButton.setOnClickListener {
            startActivity(Intent(this, EditItemActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        adapter = GameAdapter { item ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_ID, item.id)
            startActivity(intent)
        }
        binding.itemsList.layoutManager = LinearLayoutManager(this)
        binding.itemsList.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        showItems()
    }

    private fun showItems() {
        val items = repository.getItems()
        adapter.submitList(items)
        
        binding.emptyState.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
    }
}
