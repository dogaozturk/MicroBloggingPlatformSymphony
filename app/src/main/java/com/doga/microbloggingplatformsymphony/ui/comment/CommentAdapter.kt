package com.doga.microbloggingplatformsymphony.ui.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.doga.microbloggingplatformsymphony.R
import com.doga.microbloggingplatformsymphony.databinding.ItemCommentBinding
import com.doga.microbloggingplatformsymphony.domain.model.Comment

class CommentAdapter(private val retryCallback: () -> Unit
) : PagedListAdapter<Comment, CommentAdapter.CommentViewHolder>(AUTHOR_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val mDeveloperListItemBinding = DataBindingUtil.inflate<ItemCommentBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_comment, parent, false
        )
        return CommentViewHolder(mDeveloperListItemBinding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    class CommentViewHolder(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Comment) {
            binding.data = data
            Glide.with(binding.ivCommentAvatar.context)
                .load(data.avatarUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.ivCommentAvatar)
            binding.executePendingBindings()
        }
    }

    companion object {
        private val AUTHOR_COMPARATOR = object : DiffUtil.ItemCallback<Comment>() {
            override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean =
                oldItem == newItem
        }
    }
}