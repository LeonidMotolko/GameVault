package com.example.gamevault.data

import com.example.gamevault.model.LibraryItem
import com.example.gamevault.storage.ItemStorage

class SharedPrefsLibraryRepository(private val storage: ItemStorage) : LibraryRepository {
    private val items = storage.loadItems().toMutableList()

    override fun getItems(): List<LibraryItem> = items.sortedBy { it.title }

    override fun getItem(id: Long): LibraryItem? = items.firstOrNull { it.id == id }

    override fun addItem(item: LibraryItem) {
        items.add(item)
        storage.saveItems(items)
    }

    override fun updateItem(item: LibraryItem) {
        val index = items.indexOfFirst { it.id == item.id }
        if (index != -1) {
            items[index] = item
            storage.saveItems(items)
        }
    }

    override fun deleteItem(id: Long) {
        items.removeAll { it.id == id }
        storage.saveItems(items)
    }
}
