package com.doga.microbloggingplatformsymphony.domain.repository.author.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.doga.microbloggingplatformsymphony.domain.model.Author
import com.doga.microbloggingplatformsymphony.domain.repository.BlogWebService

class DataSourceFactory(
private val githubApi: BlogWebService) : DataSource.Factory<Int, Author>() {

    val authorData = MutableLiveData<AuthorDataSource>()

    override fun create(): DataSource<Int, Author> {
        val source =
            AuthorDataSource(
                githubApi
            )
        this.authorData.postValue(source)
        return source
    }

    fun getData() : MutableLiveData<AuthorDataSource> {
        return authorData
    }

}