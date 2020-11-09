package com.doga.microbloggingplatformsymphony.ui.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.doga.microbloggingplatformsymphony.domain.Listing
import com.doga.microbloggingplatformsymphony.domain.model.Comment
import com.doga.microbloggingplatformsymphony.domain.usecase.CommentUseCase
import javax.inject.Inject

class CommentViewModel @Inject constructor(
    private val commentUseCase : CommentUseCase
) : ViewModel() {

    private var commentResult : Listing<Comment>? = null

    internal var comments : LiveData<PagedList<Comment>>? = commentResult?.pagedList

    fun init (authorId : Int) {
        commentResult = commentUseCase.getPosts(authorId, PAGE_SIZE)
        comments = commentResult?.pagedList
    }

    fun retry() {
        val listing = commentResult
        listing?.retry?.invoke()
    }

    companion object {
        const val PAGE_SIZE: Int = 5
    }
}