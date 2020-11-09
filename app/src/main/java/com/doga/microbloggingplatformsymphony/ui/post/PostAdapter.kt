package com.doga.microbloggingplatformsymphony.ui.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.doga.microbloggingplatformsymphony.R
import com.doga.microbloggingplatformsymphony.databinding.ItemPostBinding
import com.doga.microbloggingplatformsymphony.domain.model.Post


class PostAdapter(val itemClickListener: PostItemClickListener,
                    private val retryCallback: () -> Unit
) : PagedListAdapter<Post,PostAdapter.PostViewHolder>(AUTHOR_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val mDeveloperListItemBinding = DataBindingUtil.inflate<ItemPostBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_post, parent, false
        )
        return PostViewHolder(mDeveloperListItemBinding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.binding.root.setOnClickListener{
            itemClickListener.onItemClicked(holder.binding.data!!)
        }
    }

    class PostViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Post) {
            binding.data = data
            Glide.with(binding.ivPost.context)
                .load(data.imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.ivPost)
            binding.executePendingBindings()
        }
    }

    interface PostItemClickListener {
        fun onItemClicked(author: Post)
    }

    companion object {
        private val AUTHOR_COMPARATOR = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
                oldItem == newItem
        }
    }
}