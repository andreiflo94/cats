package com.heixss.cats.di.module

import androidx.lifecycle.ViewModel
import com.heixss.cats.di.app.ViewModelKey
import com.heixss.cats.viewmodels.BreedDetailsViewModel
import com.heixss.cats.viewmodels.BreedsViewModel
import com.heixss.cats.viewmodels.LoginViewModel
import com.heixss.cats.viewmodels.StartupViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StartupViewModel::class)
    abstract fun bindStartupViewModel(viewModel: StartupViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BreedsViewModel::class)
    abstract fun bindBreedsViewModel(viewModel: BreedsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BreedDetailsViewModel::class)
    abstract fun bindBreedDetailsViewModel(viewModel: BreedDetailsViewModel): ViewModel
}
