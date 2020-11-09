package com.doga.microbloggingplatformsymphony.di.author

import com.doga.microbloggingplatformsymphony.domain.usecase.*
import dagger.Binds
import dagger.Module

@Module
abstract class AuthorDomainModule {

    @Binds
    internal abstract fun provideAuthorUseCase(authorUseCaseImpl: AuthorUseCaseImpl) : AuthorUseCase

    @Binds
    internal abstract fun providePostUseCase(postUseCaseImpl: PostUseCaseImpl) : PostUseCase

    @Binds
    internal abstract fun provideCommentUseCase(commentUseCaseImpl: CommentUseCaseImpl) : CommentUseCase
}