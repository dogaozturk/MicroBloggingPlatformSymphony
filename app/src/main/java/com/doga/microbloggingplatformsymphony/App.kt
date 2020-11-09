package com.doga.microbloggingplatformsymphony

import android.app.Application
import android.content.Context
import com.doga.microbloggingplatformsymphony.di.component.AppComponent
import com.doga.microbloggingplatformsymphony.di.component.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : Application() , HasAndroidInjector{

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Any>
    lateinit var blogComponent: AppComponent

    override fun androidInjector() = activityInjector

    override fun onCreate() {
        super.onCreate()
        blogComponent = initDagger(this)
        blogComponent.inject(this)
        context = applicationContext
    }

    private fun initDagger(app: App): AppComponent =
        DaggerAppComponent.builder()
            .application(this)
            .build()

    companion object {
        var context: Context? = null
    }
}