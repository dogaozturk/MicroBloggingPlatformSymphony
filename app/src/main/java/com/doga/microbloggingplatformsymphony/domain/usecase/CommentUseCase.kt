package com.doga.microbloggingplatformsymphony.domain.usecase

import com.doga.microbloggingplatformsymphony.domain.Listing
import com.doga.microbloggingplatformsymphony.domain.model.Comment
import com.doga.microbloggingplatformsymphony.domain.repository.comment.CommentRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommentUseCaseImpl @Inject constructor(private val repository: CommentRepository) : CommentUseCase{
    override fun getPosts(postId : Int, page : Int) : Listing<Comment> {
        return repository.getComments(postId, page)
    }
}

interface CommentUseCase {
    fun getPosts(postId : Int, page : Int) : Listing<Comment>
}