package com.doga.microbloggingplatformsymphony.di.component

import com.doga.microbloggingplatformsymphony.TestApp
import com.doga.microbloggingplatformsymphony.di.module.ActivityModule
import com.doga.microbloggingplatformsymphony.di.module.DomainModule
import com.doga.microbloggingplatformsymphony.di.module.FragmentModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ActivityModule::class,
    FragmentModule::class, TestAppModule::class, DomainModule::class])
interface TestAppComponent : AndroidInjector<TestApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: TestApp): Builder

        fun build(): TestAppComponent
    }

    override fun inject(app: TestApp)
}