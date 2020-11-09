package com.doga.microbloggingplatformsymphony.domain.repository.comment.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.doga.microbloggingplatformsymphony.domain.model.Comment
import com.doga.microbloggingplatformsymphony.domain.repository.BlogWebService

class CommentDataSourceFactory(private val authorId : Int,
                               private val githubApi: BlogWebService
                            ) : DataSource.Factory<Int, Comment>() {

    val postData = MutableLiveData<CommentDataSource>()

    override fun create(): DataSource<Int, Comment> {
        val source = CommentDataSource(authorId, githubApi)
        this.postData.postValue(source)
        return source
    }
}