package com.doga.microbloggingplatformsymphony.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.doga.microbloggingplatformsymphony.di.ViewModelFactory
import com.doga.microbloggingplatformsymphony.di.key.ViewModelKey
import com.doga.microbloggingplatformsymphony.ui.author.AuthorViewModel
import com.doga.microbloggingplatformsymphony.ui.comment.CommentViewModel
import com.doga.microbloggingplatformsymphony.ui.post.PostViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthorViewModel::class)
    abstract fun bindAuthorViewModel(authorViewModel: AuthorViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PostViewModel::class)
    abstract fun bindPostViewModel(postViewModel: PostViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CommentViewModel::class)
    abstract fun bindCommentViewModel(commentViewModel: CommentViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}