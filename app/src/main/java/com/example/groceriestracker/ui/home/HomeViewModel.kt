package com.example.groceriestracker.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.groceriestracker.database.AppDatabase
import com.example.groceriestracker.database.Item
import com.example.groceriestracker.repository.ItemRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val repository: ItemRepository
    val allItems: LiveData<List<Item>>

    init {
        val itemDao = AppDatabase.getDatabase(application).itemDao()
        repository = ItemRepository(itemDao)
        allItems = repository.allItems
    }

    fun insertItem(item: Item) = viewModelScope.launch {
        repository.insert(item)
    }
}