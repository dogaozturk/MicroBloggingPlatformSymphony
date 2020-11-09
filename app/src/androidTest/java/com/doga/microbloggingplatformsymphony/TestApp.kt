package com.doga.microbloggingplatformsymphony

import android.app.Application
import android.content.Context
import com.doga.microbloggingplatformsymphony.di.component.DaggerTestAppComponent
import com.doga.microbloggingplatformsymphony.di.component.TestAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

open class TestApp : Application() , HasAndroidInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Any>
    lateinit var blogComponent: TestAppComponent

    override fun androidInjector() = activityInjector

    override fun onCreate() {
        super.onCreate()
        blogComponent = initDagger(this)
        blogComponent.inject(this)
        context = applicationContext
    }

    private fun initDagger(app: TestApp): TestAppComponent =
        DaggerTestAppComponent.builder()
            .application(this)
            .build()

    companion object {
        var context: Context? = null
    }
}