package com.heixss.cats.di.module

import com.heixss.cats.ui.activities.BreedsActivity
import com.heixss.cats.ui.activities.LoginActivity
import com.heixss.cats.ui.activities.StartupActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun bindStartupActivity(): StartupActivity

    @ContributesAndroidInjector
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    abstract fun bindCatsActivity(): BreedsActivity
}