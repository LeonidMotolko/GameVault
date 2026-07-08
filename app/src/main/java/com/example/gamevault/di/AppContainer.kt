package com.example.gamevault.di

import android.content.Context
import com.example.gamevault.data.LibraryRepository
import com.example.gamevault.data.SharedPrefsLibraryRepository
import com.example.gamevault.storage.ItemStorage

class AppContainer(context: Context) {
    val repository: LibraryRepository = SharedPrefsLibraryRepository(ItemStorage(context))
}
