package com.doga.microbloggingplatformsymphony.di.module

import com.doga.microbloggingplatformsymphony.ui.author.AuthorDetailFragment
import com.doga.microbloggingplatformsymphony.ui.author.AuthorListFragment
import com.doga.microbloggingplatformsymphony.ui.post.PostDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeAuthorListFragment(): AuthorListFragment

    @ContributesAndroidInjector
    abstract fun contributeAuthorDetailFragment(): AuthorDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): PostDetailFragment
}