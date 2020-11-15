package com.android.babakmhz.newyorktimetopstories.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.babakmhz.newyorktimetopstories.data.Result
import com.android.babakmhz.newyorktimetopstories.databinding.ItemStoriesBinding
import com.bumptech.glide.Glide
import java.util.*


class ItemsRecyclerAdapter(
    private val context: Context,
    private var stories: ArrayList<Result>,
    private val callback: callBack
) : RecyclerView.Adapter<ItemsRecyclerAdapter.viewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val templateBinding =
            ItemStoriesBinding.inflate(LayoutInflater.from(context), parent, false)
        return viewHolder(
            context,
            templateBinding,
            templateBinding.root,
            callback
        )
    }

    override fun getItemCount(): Int {
        return this.stories.size
    }

    fun reloadItems(items: List<Result>) {
        stories.clear()
        stories.addAll(items)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(stories[position])
    }

    class viewHolder(
        val context: Context,
        private val itemsTemplateBinding: ItemStoriesBinding,
        itemView: View,
        private val callback: callBack
    ) : RecyclerView.ViewHolder(itemView) {

        object dBindingAdapter {
            @BindingAdapter("imageUrl")
            @JvmStatic
            fun loadImage(view: ImageView, url: String?) {
                Glide.with(view).load(url)
                    .into(view)
            }
        }

        fun bind(story: Result) {
            itemsTemplateBinding.repo = story
            itemsTemplateBinding.executePendingBindings()
            itemsTemplateBinding.itemContainer.setOnClickListener {
                callback.onItemClicked(story)
            }

        }

    }

}

public interface callBack {
    fun onItemClicked(story: Result)
}
