package com.doga.microbloggingplatformsymphony.domain.repository.comment.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.doga.microbloggingplatformsymphony.domain.NetworkState
import com.doga.microbloggingplatformsymphony.domain.model.Comment
import com.doga.microbloggingplatformsymphony.domain.repository.BlogWebService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CommentDataSource(private val authorId : Int,
                        private val blogWebService: BlogWebService)
    : PageKeyedDataSource<Int, Comment>() {

    var retry: (() -> Any)? = null
    val network = MutableLiveData<NetworkState>()
    val initial = MutableLiveData<NetworkState>()

    /**
     * This callback loads the very first page of the response.
     *
     */
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Comment>) {
        val currentPage = 1
        var resultList = mutableListOf<Comment>()
        blogWebService.getComments(
            authorId = authorId,
            page = currentPage,
            sort = "date",
            order = "asc").enqueue(object : Callback<List<Comment>> {
            override fun onResponse(
                call: Call<List<Comment>>,
                response: Response<List<Comment>>) {
                Log.e("TAG", "DATA REFRESHED FROM NETWORK")
                resultList = response.body()!!.toMutableList()
                callback.onResult(resultList, 0, currentPage+1)
            }
            override fun onFailure(
                call: Call<List<Comment>?>,
                t: Throwable) {
                Log.e("TAG", "DATA NOT REFRESHED FROM NETWORK")
            }
        })

    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Comment>) {
        val currentPage = params.key
        var resultList = mutableListOf<Comment>()
        blogWebService.getComments(
            authorId = authorId,
            page = currentPage,
            sort = "date",
            order = "asc").enqueue(object : Callback<List<Comment>> {
            override fun onResponse(
                call: Call<List<Comment>>,
                response: Response<List<Comment>>) {
                Log.e("TAG", "DATA REFRESHED FROM NETWORK")
                resultList = response.body()!!.toMutableList()
                callback.onResult(resultList, currentPage + 1)
            }
            override fun onFailure(
                call: Call<List<Comment>?>,
                t: Throwable) {
                Log.e("TAG", "DATA NOT REFRESHED FROM NETWORK")
            }
        })

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Comment>) {
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