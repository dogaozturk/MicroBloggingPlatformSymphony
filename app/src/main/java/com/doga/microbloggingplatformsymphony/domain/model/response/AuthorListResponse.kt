package com.doga.microbloggingplatformsymphony.domain.model.response

import com.doga.microbloggingplatformsymphony.domain.model.Author

data class AuthorListResponse(
    val authors: List<Author>
)