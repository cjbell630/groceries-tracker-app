package com.example.groceriestracker.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groceriestracker.database.Item
import com.example.groceriestracker.databinding.FragmentHomeBinding
import com.example.groceriestracker.ui.ItemDetails

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: ItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ItemAdapter()
        binding.recyclerView.adapter = adapter

        homeViewModel.allItems.observe(viewLifecycleOwner) { items ->
            items?.let { adapter.submitList(it) }
        }

        // Applying OnClickListener to our Adapter
        adapter.setOnClickListener(object :
            ItemAdapter.OnClickListener {
            override fun onClick(position: Int, model: Item) {
                val intent = Intent(this@HomeFragment.context, ItemDetails::class.java)
                // Passing the data to the
                // EmployeeDetails Activity
                intent.putExtra(NEXT_SCREEN, model)
                startActivity(intent)
            }
        })

        binding.buttonAddItem.setOnClickListener {
            try {
                val newItem = Item(uid = 0, name = "Test Item", amount = 1.0, unit = "pcs", statusEvents = emptyList())
                homeViewModel.insertItem(newItem)
            } catch (e: Exception) {
                Log.e("HomeFragment", "Error inserting item", e)
            }
        }

        return root
    }

    companion object {
        const val NEXT_SCREEN="details_screen"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}