package com.douglasrondini.drive_20_android.di

import com.douglasrondini.drive_20_android.data.remote.ApiService
import com.douglasrondini.drive_20_android.data.repository.UserRepositoryImpl
import com.douglasrondini.drive_20_android.domain.repository.UserRepository
import com.douglasrondini.drive_20_android.domain.usecase.ExampleUseCase
import org.koin.dsl.module
import retrofit2.Retrofit

val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
}

val useCaseModule = module {
    factory { ExampleUseCase(get()) }
}

val viewModelModule = module {
    // Exemplo: viewModel { MainViewModel(get()) }
}

val apiModule = module {
    single { get<Retrofit>().create(ApiService::class.java) }
}

val appModule = module {
    includes(networkModule, apiModule, repositoryModule, useCaseModule, viewModelModule)
}
