package com.example.groceriestracker.ui

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.groceriestracker.MainActivity
import com.example.groceriestracker.database.Item
import com.example.groceriestracker.databinding.ActivityItemDetailsBinding
import com.example.groceriestracker.ui.home.HomeFragment


class ItemDetails : AppCompatActivity() {
    private lateinit var binding: ActivityItemDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar) // applies action bar in the view
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) // enables back button
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        // creating an employee list
        // of type Employee class
        var emplist: Item? = null

        // checking if the intent has extra
        if (intent.hasExtra(HomeFragment.NEXT_SCREEN)) {
            // get the Serializable data model class with the details in it
            // getSerializableExtra(name) as T deprecated in API 31 so use getSerializableExtra(name, class) in newer versions
            emplist = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                intent.getSerializableExtra(HomeFragment.NEXT_SCREEN, Item::class.java)
            else
                intent.getSerializableExtra(HomeFragment.NEXT_SCREEN) as Item
        }
        // it the emplist is not null the it has some data and display it
        binding.displayName.text = emplist!!.name   // displaying name
        binding.displayAmount.text = emplist.amount.toString()   // displaying name
        binding.displayUnit.text = emplist.unit // displaying unit

    }
}