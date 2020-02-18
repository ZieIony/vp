package com.vp.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vp.list.model.ListItem


typealias OnItemClickListener = (String) -> Unit

class ListAdapter : RecyclerView.Adapter<ListViewHolder>() {
    private var listItems: ArrayList<ListItem> = ArrayList()
    private var onItemClickListener = EMPTY_ON_ITEM_CLICK_LISTENER

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) =
            holder.bind(listItems[position], onItemClickListener)

    override fun getItemCount() = listItems.size

    fun setItems(listItems: List<ListItem>) {
        this.listItems = ArrayList(listItems)
        notifyDataSetChanged()
    }

    fun clearItems() {
        listItems.clear()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener ?: EMPTY_ON_ITEM_CLICK_LISTENER
    }

    companion object {
        const val NO_IMAGE = "N/A"
        private val EMPTY_ON_ITEM_CLICK_LISTENER: OnItemClickListener = { }
    }
}
