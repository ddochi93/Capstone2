package com.example.harusikdan.koin.modules

import com.example.harusikdan.koin.repository.AccessRetrofitRepository
import com.example.harusikdan.koin.repositoryImpl.AccessRetrofitRepositoryImpl
import org.koin.dsl.module

val networkModule = module {
    single<AccessRetrofitRepository> { AccessRetrofitRepositoryImpl() }
}