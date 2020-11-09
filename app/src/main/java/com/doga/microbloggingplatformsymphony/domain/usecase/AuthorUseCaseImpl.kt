package com.doga.microbloggingplatformsymphony.domain.usecase

import com.doga.microbloggingplatformsymphony.domain.Listing
import com.doga.microbloggingplatformsymphony.domain.model.Author
import com.doga.microbloggingplatformsymphony.domain.repository.author.AuthorRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorUseCaseImpl @Inject constructor(private val repository: AuthorRepository) : AuthorUseCase{
    override fun getAuthors(page : Int) : Listing<Author> {
        return repository.getAuthors(page)
    }
}

interface AuthorUseCase {
    fun getAuthors(page : Int) : Listing<Author>
}