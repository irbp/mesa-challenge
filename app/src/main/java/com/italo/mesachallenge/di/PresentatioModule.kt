package com.italo.mesachallenge.di

import com.italo.mesachallenge.viewmodel.LoginViewModel
import com.italo.mesachallenge.viewmodel.MainViewModel
import com.italo.mesachallenge.viewmodel.RadarViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { RadarViewModel(useCase = get()) }
    viewModel { LoginViewModel(useCase = get()) }
    viewModel { MainViewModel(useCase = get(), userUseCase = get()) }
}