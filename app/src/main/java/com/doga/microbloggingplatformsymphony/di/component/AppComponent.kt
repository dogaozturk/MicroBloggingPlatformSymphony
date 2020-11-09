package com.doga.microbloggingplatformsymphony.di.component

import com.doga.microbloggingplatformsymphony.App
import com.doga.microbloggingplatformsymphony.di.module.ActivityModule
import com.doga.microbloggingplatformsymphony.di.module.AppModule
import com.doga.microbloggingplatformsymphony.di.module.DomainModule
import com.doga.microbloggingplatformsymphony.di.module.FragmentModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ActivityModule::class,
    FragmentModule::class, AppModule::class, DomainModule::class])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }

    override fun inject(app: App)
}
