package com.heixss.cats.di.module

import com.heixss.cats.ui.fragments.BreedDetailsFragment
import com.heixss.cats.ui.fragments.BreedsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {
    @ContributesAndroidInjector
    abstract fun bindBreedsFragment(): BreedsFragment

    @ContributesAndroidInjector
    abstract fun bindBreedDetailsFragment(): BreedDetailsFragment
}