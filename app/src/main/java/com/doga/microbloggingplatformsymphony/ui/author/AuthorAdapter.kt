package com.doga.microbloggingplatformsymphony.ui.author

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.doga.microbloggingplatformsymphony.R
import com.doga.microbloggingplatformsymphony.databinding.ItemAuthorBinding
import com.doga.microbloggingplatformsymphony.domain.model.Author


class AuthorAdapter(val itemClickListener: AuthorItemClickListener,
                    private val retryCallback: () -> Unit
                    ) : PagedListAdapter<Author,AuthorAdapter.AuthorViewHolder>(AUTHOR_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorViewHolder {
        val mDeveloperListItemBinding = DataBindingUtil.inflate<ItemAuthorBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_author, parent, false
        )
        return AuthorViewHolder(mDeveloperListItemBinding)
    }

    override fun onBindViewHolder(holder: AuthorViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.binding.root.setOnClickListener{
            itemClickListener.onItemClicked(holder.binding.data!!)
        }
    }

    class AuthorViewHolder(val binding: ItemAuthorBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Author) {
            binding.data = data
            Glide.with(binding.ivAuthor.context)
                .load(data.avatarUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.ivAuthor)
            binding.executePendingBindings()
        }
    }

    interface AuthorItemClickListener {
        fun onItemClicked(author: Author)
    }

    companion object {
        private val AUTHOR_COMPARATOR = object : DiffUtil.ItemCallback<Author>() {
            override fun areItemsTheSame(oldItem: Author, newItem: Author): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Author, newItem: Author): Boolean =
                oldItem == newItem
        }
    }
}