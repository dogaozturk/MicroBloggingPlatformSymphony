package com.doga.microbloggingplatformsymphony.di.component

import com.doga.microbloggingplatformsymphony.di.common.Constants
import com.doga.microbloggingplatformsymphony.di.module.ViewModelModule
import com.doga.microbloggingplatformsymphony.domain.repository.BlogWebService
import com.doga.microbloggingplatformsymphony.domain.repository.author.AuthorRepository
import com.doga.microbloggingplatformsymphony.domain.usecase.AuthorUseCase
import com.doga.microbloggingplatformsymphony.domain.usecase.CommentUseCase
import com.doga.microbloggingplatformsymphony.domain.usecase.PostUseCase
import com.doga.microbloggingplatformsymphony.ui.author.AuthorViewModel
import com.doga.microbloggingplatformsymphony.ui.comment.CommentViewModel
import com.doga.microbloggingplatformsymphony.ui.post.PostViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class TestAppModule {

    // --- REPOSITORY INJECTION ---
    @Provides
    @Singleton
    fun provideExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }

    @Provides
    @Singleton
    fun provideAuthorViewModel(
        useCase: AuthorUseCase
    ): AuthorViewModel {
        return AuthorViewModel(useCase)
    }

    @Provides
    @Singleton
    fun providePostViewModel(
        useCase: PostUseCase
    ): PostViewModel {
        return PostViewModel(useCase)
    }

    @Provides
    @Singleton
    fun provideCommentViewModel(
        useCase: CommentUseCase
    ): CommentViewModel {
        return CommentViewModel(useCase)
    }

    @Provides
    @Singleton
    fun provideAuthorRepository(
        webservice: BlogWebService,
        executor: Executor
    ): AuthorRepository {
        return AuthorRepository(
            webservice,
            executor
        )
    }

    // --- NETWORK INJECTION ---
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson?): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(Constants.TEST_BASE_URL)
            .build()
    }

    @Provides
    open fun httpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        val clientBuilder = OkHttpClient.Builder()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(httpLoggingInterceptor)
        clientBuilder.readTimeout(120, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(120, TimeUnit.SECONDS)
        return clientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideBlogWebService(restAdapter: Retrofit): BlogWebService {
        return restAdapter.create<BlogWebService>(
            BlogWebService::class.java)
    }
}