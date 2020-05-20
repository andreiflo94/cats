package com.heixss.cats.di.app

import com.heixss.cats.CatsApp
import com.heixss.cats.di.module.ActivityBindingModule
import com.heixss.cats.di.module.AppModule
import com.heixss.cats.di.module.FragmentBindingModule
import com.heixss.cats.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelModule::class,
        AppModule::class,
        FragmentBindingModule::class,
        ActivityBindingModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: CatsApp): Builder

        fun build(): AppComponent
    }

    fun inject(application: CatsApp)
}