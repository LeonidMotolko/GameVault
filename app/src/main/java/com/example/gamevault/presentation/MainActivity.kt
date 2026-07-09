package com.example.gamevault.presentation

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamevault.GameVaultApplication
import com.example.gamevault.R
import com.example.gamevault.data.LibraryRepository
import com.example.gamevault.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: LibraryRepository
    private lateinit var adapter: GameAdapter
    private var isGridView = false
    private var currentSearchQuery = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        repository = (application as GameVaultApplication).appContainer.repository

        setupEdgeToEdge()
        setupRecyclerView()

        binding.addButton.setOnClickListener {
            startActivity(Intent(this, EditItemActivity::class.java))
        }
    }

    private fun setupEdgeToEdge() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, 0)
            
            // Apply padding to AppBarLayout for status bar
            binding.toolbar.setPadding(0, systemBars.top, 0, 0)
            
            // FAB should avoid the navigation bar
            val params = binding.addButton.layoutParams as android.view.ViewGroup.MarginLayoutParams
            params.bottomMargin = systemBars.bottom + (16 * resources.displayMetrics.density).toInt()
            binding.addButton.layoutParams = params
            
            insets
        }
    }

    private fun setupRecyclerView() {
        adapter = GameAdapter { item ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_ID, item.id)
            startActivity(intent)
        }
        updateLayoutManager()
        binding.itemsList.adapter = adapter
    }

    private fun updateLayoutManager() {
        binding.itemsList.layoutManager = if (isGridView) {
            GridLayoutManager(this, 2)
        } else {
            LinearLayoutManager(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                currentSearchQuery = newText ?: ""
                showItems()
                return true
            }
        })
        
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_toggle_view -> {
                isGridView = !isGridView
                updateLayoutManager()
                item.setIcon(if (isGridView) android.R.drawable.ic_menu_sort_by_size else android.R.drawable.ic_dialog_dialer)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        showItems()
    }

    private fun showItems() {
        val items = repository.getItems().filter { 
            it.title.contains(currentSearchQuery, ignoreCase = true) || 
            it.genre.contains(currentSearchQuery, ignoreCase = true)
        }
        adapter.submitList(items)
        
        binding.emptyState.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
    }
}
