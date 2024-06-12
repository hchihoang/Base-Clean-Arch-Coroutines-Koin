package com.inetkr.base.di

import com.inetkr.base.data.repository.HomeRemoteRepository
import com.inetkr.base.data.source.remote.APIService
import com.inetkr.base.domain.usecase.GetNotesUseCase
import com.inetkr.base.domain.usecase.LoginUseCase
import com.inetkr.base.domain.usecase.SignUpUseCase
import com.inetkr.base.presentation.ui.home.HomeViewModel
import com.inetkr.base.presentation.ui.login.LoginViewModel
import com.inetkr.base.presentation.ui.main.MainViewModel
import com.inetkr.base.presentation.ui.signup.SignUpViewModel
import com.inetkr.base.presentation.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {

    viewModel { MainViewModel(get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { SignUpViewModel(get(), get(), get()) }

    single { HomeRemoteRepository(get()) }
    single { GetNotesUseCase(get()) }
    single { LoginUseCase(get()) }
    single { SignUpUseCase(get()) }
}