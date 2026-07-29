package com.douglasrondini.drive_20_android.di

import com.douglasrondini.drive_20_android.data.remote.ApiService
import com.douglasrondini.drive_20_android.data.local.PreferenceManager
import com.douglasrondini.drive_20_android.data.repository.AlunoRepositoryImpl
import com.douglasrondini.drive_20_android.data.repository.LoginRepositoryImpl
import com.douglasrondini.drive_20_android.data.repository.UserRepositoryImpl
import com.douglasrondini.drive_20_android.domain.repository.AlunoRepository
import com.douglasrondini.drive_20_android.domain.repository.LoginRepository
import com.douglasrondini.drive_20_android.domain.repository.UserRepository
import com.douglasrondini.drive_20_android.domain.home.aluno.RegisterAlunoUseCase
import com.douglasrondini.drive_20_android.domain.usecase.LoginUseCase
import com.douglasrondini.drive_20_android.ui.register.aluno.RegisterAlunoViewModel
import com.douglasrondini.drive_20_android.ui.login.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val localModule = module {
    single { PreferenceManager(androidContext()) }
}

val repositoryModule = module {
    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
    singleOf(::AlunoRepositoryImpl) { bind<AlunoRepository>() }
    singleOf(::LoginRepositoryImpl) { bind<LoginRepository>() }
}

val useCaseModule = module {
    factoryOf(::RegisterAlunoUseCase)
    factoryOf(::LoginUseCase)
}

val viewModelModule = module {
    viewModelOf(::RegisterAlunoViewModel)
    viewModelOf(::LoginViewModel)
}

val apiModule = module {
    single { get<Retrofit>().create(ApiService::class.java) }
}

val appModule = module {
    includes(networkModule, localModule, apiModule, repositoryModule, useCaseModule, viewModelModule)
}
