package com.android.babakmhz.newyorktimetopstories.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.babakmhz.newyorktimetopstories.R
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

        private fun toggleImageBookmark(bookmarked: Boolean, imageView: ImageView) {
            if (bookmarked) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_baseline_bookmark_24
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_baseline_bookmark_border_24
                    )
                )
            }
        }

        fun bind(story: Result) {
            itemsTemplateBinding.repo = story
            itemsTemplateBinding.executePendingBindings()
            val imageView: ImageView = itemView.findViewById(R.id.poster)
            try {
                Glide.with(context).load(story.multimedia[0].url)
                    .into(imageView)
            } catch (e: Exception) {
            }

            val imageBookmark: ImageView = itemView.findViewById(R.id.image_bookmark)
            //you do this with data binding also
            if (story.bookmarked) {
                imageBookmark.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_baseline_bookmark_24
                    )
                )
            }

            imageBookmark.setOnClickListener {
                story.bookmarked = !story.bookmarked
                toggleImageBookmark(story.bookmarked, it as ImageView)
                callback.onBookmarkClicked(story.bookmarked, story)
            }

            itemsTemplateBinding.itemContainer.setOnClickListener {
                callback.onItemClicked(story)
            }
        }
    }

}

public interface callBack {
    fun onItemClicked(story: Result)
    fun onBookmarkClicked(added: Boolean = false, story: Result)
}
