package com.example.smartgrocery.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.smartgrocery.R
import com.example.smartgrocery.grocery_lists.ListsFragment
import com.example.smartgrocery.grocery_lists.ProductItemsFragment

import com.example.smartgrocery.repositories.GroceryListItems
import com.example.smartgrocery.repositories.ProductRepository


import kotlinx.android.synthetic.main.layout_list_item.view.*

/**
 * [RecyclerView.Adapter] that can display a List Items and makes a call to the
 * specified [OnGroceryListFragmentInteractionListener].
 */
class ProductListItemAdapter(
    private val mValues: List<ProductRepository.ProductDataItem>,       // List<GroceryListData>
    private val mListenerProduct: ProductItemsFragment.OnProductListFragmentInteractionListener?
) : RecyclerView.Adapter<ProductListItemAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as ProductRepository.ProductDataItem // TODO: Zu Produkt Datentype ändern
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListenerProduct?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_product_list_items, parent, false)
        return ViewHolder(view)
    }

    // TODO: Ausgabe auf die Textviews anpassen
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mContentView.text = item.name_produkte

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.list_name
        val mContentView: TextView = mView.test_view

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}