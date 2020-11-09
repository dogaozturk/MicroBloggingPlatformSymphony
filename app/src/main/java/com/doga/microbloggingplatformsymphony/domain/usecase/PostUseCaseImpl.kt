package com.doga.microbloggingplatformsymphony.domain.usecase

import com.doga.microbloggingplatformsymphony.domain.Listing
import com.doga.microbloggingplatformsymphony.domain.model.Post
import com.doga.microbloggingplatformsymphony.domain.repository.post.PostRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostUseCaseImpl @Inject constructor(private val repository: PostRepository) : PostUseCase{
    override fun getPosts(authorId : Int, page : Int) : Listing<Post> {
        return repository.getPosts(authorId, page)
    }
}

interface PostUseCase {
    fun getPosts(authorId : Int, page : Int) : Listing<Post>
}