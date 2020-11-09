package com.doga.microbloggingplatformsymphony.domain.repository.author

import androidx.lifecycle.Transformations.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.doga.microbloggingplatformsymphony.domain.Listing
import com.doga.microbloggingplatformsymphony.domain.model.Author
import com.doga.microbloggingplatformsymphony.domain.repository.BlogWebService
import com.doga.microbloggingplatformsymphony.domain.repository.author.datasource.DataSourceFactory
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorRepository @Inject constructor(
    private val webservice: BlogWebService,
    private val executor: Executor) {

    fun getAuthors(page : Int) : Listing<Author> {
        val factory = dataSourceFactory(webservice)

        val config = pagedListConfig(page)

        val livePagedList = LivePagedListBuilder(factory, config)
            .setFetchExecutor(executor)
            .build()

        return Listing(
            pagedList = livePagedList,
            networkState = switchMap(factory.authorData) { it.network },
            retry = { factory.authorData.value?.retryAllFailed() },
            refresh = { factory.authorData.value?.invalidate() },
            refreshState = switchMap(factory.authorData) { it.initial })
    }


    private fun dataSourceFactory(webservice: BlogWebService): DataSourceFactory {
        return DataSourceFactory(
            webservice
        )
    }

    private fun pagedListConfig(pageSize: Int): PagedList.Config {
        return PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(pageSize * 2)
            .setPageSize(pageSize)
            .build()
    }
}