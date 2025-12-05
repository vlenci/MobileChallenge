package com.example.mobilechallenge

import com.example.mobilechallenge.repositories.LoginRepository
import com.example.mobilechallenge.repositories.LoginRepositoryImpl
import com.example.mobilechallenge.repositories.TreeRepository
import com.example.mobilechallenge.repositories.TreeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindLoginRepository(
        loginRepositoryImpl: LoginRepositoryImpl
    ) : LoginRepository

    @Binds
    @Singleton
    abstract fun bindTreeRepository(
        treeRepositoryImpl: TreeRepositoryImpl
    ) : TreeRepository
}