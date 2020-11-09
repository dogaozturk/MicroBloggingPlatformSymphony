package com.doga.microbloggingplatformsymphony.ui.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.doga.microbloggingplatformsymphony.domain.Listing
import com.doga.microbloggingplatformsymphony.domain.model.Post
import com.doga.microbloggingplatformsymphony.domain.usecase.PostUseCase
import javax.inject.Inject


class PostViewModel @Inject constructor(
    private val postUseCase : PostUseCase
) : ViewModel() {

    private var postResult : Listing<Post>? = null

    internal var posts : LiveData<PagedList<Post>>? = postResult?.pagedList

    fun init (authorId : Int) {
        postResult = postUseCase.getPosts(authorId, PAGE_SIZE)
        posts = postResult?.pagedList
    }

    fun retry() {
        val listing = postResult
        listing?.retry?.invoke()
    }

    companion object {
        const val PAGE_SIZE: Int = 5
    }
}