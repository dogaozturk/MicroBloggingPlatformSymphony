package com.doga.microbloggingplatformsymphony.domain.repository.post.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.doga.microbloggingplatformsymphony.domain.model.Post
import com.doga.microbloggingplatformsymphony.domain.repository.BlogWebService
import com.doga.microbloggingplatformsymphony.domain.repository.comment.datasource.CommentDataSource

class PostDataSourceFactory(private val authorId : Int,
                            private val githubApi: BlogWebService
                            ) : DataSource.Factory<Int, Post>() {

    val postData = MutableLiveData<PostDataSource>()

    override fun create(): DataSource<Int, Post> {
        val source = PostDataSource(authorId, githubApi)
        this.postData.postValue(source)
        return source
    }
}