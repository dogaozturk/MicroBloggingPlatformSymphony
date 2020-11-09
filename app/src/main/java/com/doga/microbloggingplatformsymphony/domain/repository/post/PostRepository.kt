package com.doga.microbloggingplatformsymphony.domain.repository.post

import androidx.lifecycle.Transformations.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.doga.microbloggingplatformsymphony.domain.Listing
import com.doga.microbloggingplatformsymphony.domain.model.Post
import com.doga.microbloggingplatformsymphony.domain.repository.BlogWebService
import com.doga.microbloggingplatformsymphony.domain.repository.comment.datasource.CommentDataSourceFactory
import com.doga.microbloggingplatformsymphony.domain.repository.post.datasource.PostDataSourceFactory
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepository @Inject constructor(
    private val webservice: BlogWebService,
    private val executor: Executor) {

    fun getPosts(authorId : Int, page : Int) : Listing<Post> {
        val factory = dataSourceFactory(authorId, webservice)

        val config = pagedListConfig(page)

        val livePagedList = LivePagedListBuilder(factory, config)
            .setFetchExecutor(executor)
            .build()

        return Listing(
            pagedList = livePagedList,
            networkState = switchMap(factory.postData) { it.network },
            retry = { factory.postData.value?.retryAllFailed() },
            refresh = { factory.postData.value?.invalidate() },
            refreshState = switchMap(factory.postData) { it.initial })
    }


    private fun dataSourceFactory(authorId : Int,
                                  webservice: BlogWebService): PostDataSourceFactory {
        return PostDataSourceFactory(authorId, webservice)
    }

    private fun pagedListConfig(pageSize: Int): PagedList.Config {
        return PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(pageSize * 2)
            .setPageSize(pageSize)
            .build()
    }
}