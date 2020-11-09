package com.doga.microbloggingplatformsymphony.ui.author

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.doga.microbloggingplatformsymphony.domain.model.Author
import com.doga.microbloggingplatformsymphony.domain.usecase.AuthorUseCase
import javax.inject.Inject

class AuthorViewModel @Inject constructor(
    authorUseCase : AuthorUseCase
    ) : ViewModel() {

    private val authorResult = authorUseCase.getAuthors(PAGE_SIZE)

    var authors : LiveData<PagedList<Author>> = authorResult.pagedList

    fun retry() {
        val listing = authorResult
        listing.retry.invoke()
    }

    companion object {
        const val PAGE_SIZE: Int = 5
    }
}
