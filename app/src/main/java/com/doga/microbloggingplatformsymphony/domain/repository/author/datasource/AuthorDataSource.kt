package com.doga.microbloggingplatformsymphony.domain.repository.author.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.doga.microbloggingplatformsymphony.domain.NetworkState
import com.doga.microbloggingplatformsymphony.domain.model.Author
import com.doga.microbloggingplatformsymphony.domain.repository.BlogWebService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AuthorDataSource(private val blogWebService: BlogWebService)
    : PageKeyedDataSource<Int, Author>() {

    var retry: (() -> Any)? = null
    val network = MutableLiveData<NetworkState>()
    val initial = MutableLiveData<NetworkState>()

    /**
     * This callback loads the very first page of the response.
     *
     */
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Author>) {
        val currentPage = 1
        var resultList = mutableListOf<Author>()
        blogWebService.getAuthors(
            page = currentPage).enqueue(object : Callback<List<Author>> {
            override fun onResponse(
                call: Call<List<Author>>,
                response: Response<List<Author>>) {
                Log.e("TAG", "DATA REFRESHED FROM NETWORK")
                resultList = response.body()!!.toMutableList()
                callback.onResult(resultList, 0, currentPage+1)
            }
            override fun onFailure(
                call: Call<List<Author>?>,
                t: Throwable) {
                Log.e("TAG", "DATA NOT REFRESHED FROM NETWORK")
            }
        })

    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Author>) {
        val currentPage = params.key
        var resultList = mutableListOf<Author>()
        blogWebService.getAuthors(
            page = currentPage).enqueue(object : Callback<List<Author>> {
            override fun onResponse(
                call: Call<List<Author>>,
                response: Response<List<Author>>) {
                Log.e("TAG", "DATA REFRESHED FROM NETWORK")
                resultList = response.body()!!.toMutableList()
                callback.onResult(resultList, currentPage + 1)
            }
            override fun onFailure(
                call: Call<List<Author>?>,
                t: Throwable) {
                Log.e("TAG", "DATA NOT REFRESHED FROM NETWORK")
            }
        })

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Author>) {
        //ignore
    }

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
//        prevRetry?.let { retry ->
//            retryExecutor.execute { retry() }
//        }
    }
}