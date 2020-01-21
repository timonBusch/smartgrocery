package com.example.smartgrocery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartgrocery.adapter.CustomAdapter

class ListsFragment : Fragment() {

    private lateinit var currentLayoutManagerType: LayoutManagerType
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var dataset: Array<String>

    enum class LayoutManagerType {  LINEAR_LAYOUT_MANAGER }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDataset()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.frament_lists, container, false)

        recyclerView = rootView.findViewById(R.id.recyclerView)

        layoutManager = LinearLayoutManager(activity)

        currentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            currentLayoutManagerType = savedInstanceState
                .getSerializable(KEY_LAYOUT_MANAGER) as LayoutManagerType
        }

        // Set CustomAdapter as the adapter for RecyclerView.
        recyclerView.adapter = CustomAdapter(dataset)

        ListsFragment.LayoutManagerType.LINEAR_LAYOUT_MANAGER
        layoutManager = LinearLayoutManager(activity)
        currentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER


        return rootView
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {

        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, currentLayoutManagerType)
        super.onSaveInstanceState(savedInstanceState)
    }

    private fun initDataset() {
        dataset = Array(DATASET_COUNT, {i -> "This is element # $i"})
    }

    companion object {
        private val TAG = "RecyclerViewFragment"
        private val KEY_LAYOUT_MANAGER = "layoutManager"
        private val SPAN_COUNT = 2
        private val DATASET_COUNT = 60
    }
}