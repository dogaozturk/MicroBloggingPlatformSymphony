package com.doga.microbloggingplatformsymphony.domain.repository.post.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.doga.microbloggingplatformsymphony.domain.NetworkState
import com.doga.microbloggingplatformsymphony.domain.model.Post
import com.doga.microbloggingplatformsymphony.domain.repository.BlogWebService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PostDataSource(private val authorId : Int,
                     private val blogWebService: BlogWebService)
    : PageKeyedDataSource<Int, Post>() {

    var retry: (() -> Any)? = null
    val network = MutableLiveData<NetworkState>()
    val initial = MutableLiveData<NetworkState>()

    /**
     * This callback loads the very first page of the response.
     *
     */
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Post>) {
        val currentPage = 1
        var resultList = mutableListOf<Post>()
        blogWebService.getPosts(
            authorId = authorId,
            page = currentPage).enqueue(object : Callback<List<Post>> {
            override fun onResponse(
                call: Call<List<Post>>,
                response: Response<List<Post>>) {
                Log.e("TAG", "DATA REFRESHED FROM NETWORK")
                resultList = response.body()!!.toMutableList()
                callback.onResult(resultList, 0, currentPage+1)
            }
            override fun onFailure(
                call: Call<List<Post>?>,
                t: Throwable) {
                Log.e("TAG", "DATA NOT REFRESHED FROM NETWORK")
            }
        })

    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Post>) {
        val currentPage = params.key
        var resultList = mutableListOf<Post>()
        blogWebService.getPosts(
            authorId = authorId,
            page = currentPage).enqueue(object : Callback<List<Post>> {
            override fun onResponse(
                call: Call<List<Post>>,
                response: Response<List<Post>>) {
                Log.e("TAG", "DATA REFRESHED FROM NETWORK")
                resultList = response.body()!!.toMutableList()
                callback.onResult(resultList, currentPage + 1)
            }
            override fun onFailure(
                call: Call<List<Post>?>,
                t: Throwable) {
                Log.e("TAG", "DATA NOT REFRESHED FROM NETWORK")
            }
        })

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Post>) {
        //ignore
    }

    fun retryAllFailed() {
    }
}