package com.doga.microbloggingplatformsymphony.di.module

import com.doga.microbloggingplatformsymphony.di.author.AuthorDomainModule
import dagger.Module

// wrapper class for domain modules
@Module(includes = [AuthorDomainModule::class])
internal abstract class DomainModule