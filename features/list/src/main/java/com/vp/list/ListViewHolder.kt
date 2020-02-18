package com.vp.list

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.vp.list.model.ListItem

class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var image: ImageView = itemView.findViewById(R.id.poster)

    fun bind(listItem: ListItem, onItemClickListener: OnItemClickListener) {
        if (listItem.poster != null && ListAdapter.NO_IMAGE != listItem.poster) {
            val density = image.resources.displayMetrics.density
            GlideApp.with(image)
                    .load(listItem.poster)
                    .override((300 * density).toInt(), (600 * density).toInt())
                    .into(image)
        } else {
            image.setImageResource(R.drawable.placeholder)
        }
        itemView.setOnClickListener { onItemClickListener(listItem.imdbID!!) }
    }

}