package com.example.gamevault.storage

import android.content.Context
import com.example.gamevault.model.LibraryItem
import org.json.JSONArray
import org.json.JSONObject

class ItemStorage(context: Context) {
    private val preferences = context.getSharedPreferences("game_vault_storage", Context.MODE_PRIVATE)

    fun loadItems(): List<LibraryItem> {
        val raw = preferences.getString(KEY_ITEMS, null) ?: return defaultItems()
        val array = JSONArray(raw)
        val result = mutableListOf<LibraryItem>()
        for (i in 0 until array.length()) {
            val item = array.getJSONObject(i)
            result.add(item.toLibraryItem())
        }
        return result
    }

    fun saveItems(items: List<LibraryItem>) {
        val array = JSONArray()
        items.forEach { array.put(it.toJson()) }
        preferences.edit().putString(KEY_ITEMS, array.toString()).apply()
    }

    private fun JSONObject.toLibraryItem(): LibraryItem {
        val id = getLong("id")
        val title = getString("title")
        val genre = getString("genre")
        val year = getInt("year")
        val rating = getInt("rating")
        val description = getString("description")
        return when (getString("type")) {
            "board" -> LibraryItem.BoardGame(
                id = id,
                title = title,
                genre = genre,
                year = year,
                rating = rating,
                description = description,
                players = getString("extra")
            )
            else -> LibraryItem.VideoGame(
                id = id,
                title = title,
                genre = genre,
                year = year,
                rating = rating,
                description = description,
                platform = getString("extra")
            )
        }
    }

    private fun LibraryItem.toJson(): JSONObject {
        val json = JSONObject()
        json.put("id", id)
        json.put("title", title)
        json.put("genre", genre)
        json.put("year", year)
        json.put("rating", rating)
        json.put("description", description)
        when (this) {
            is LibraryItem.VideoGame -> {
                json.put("type", "video")
                json.put("extra", platform)
            }
            is LibraryItem.BoardGame -> {
                json.put("type", "board")
                json.put("extra", players)
            }
        }
        return json
    }

    private fun defaultItems(): List<LibraryItem> = listOf(
        LibraryItem.VideoGame(1, "Hades", "Roguelike", 2020, 9, "Fast action game with repeated runs.", "PC"),
        LibraryItem.BoardGame(2, "Catan", "Strategy", 1995, 8, "Players collect resources and build settlements.", "3-4"),
        LibraryItem.VideoGame(3, "Stardew Valley", "Simulation", 2016, 10, "Farming, crafting and relationships in a small town.", "PC / Mobile"),
        LibraryItem.BoardGame(4, "Ticket to Ride", "Family", 2004, 7, "A railway board game with routes and cards.", "2-5")
    )

    companion object {
        private const val KEY_ITEMS = "items"
    }
}
