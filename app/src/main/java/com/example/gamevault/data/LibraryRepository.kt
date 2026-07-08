package com.example.gamevault.data

import com.example.gamevault.model.LibraryItem

interface LibraryRepository {
    fun getItems(): List<LibraryItem>
    fun getItem(id: Long): LibraryItem?
    fun addItem(item: LibraryItem)
    fun updateItem(item: LibraryItem)
    fun deleteItem(id: Long)
}
