package com.italo.mesachallenge.data.repository

import com.italo.domain.base.Failure
import com.italo.domain.base.OneOf
import com.italo.domain.model.User
import com.italo.domain.repository.UserRepository
import com.italo.mesachallenge.data.cache.source.UserCacheDataSource
import kotlinx.coroutines.runBlocking

class UserRepositoryImpl(private val cacheSource: UserCacheDataSource) : UserRepository {
    override fun getUser(): OneOf<Failure, User> = runBlocking {
        cacheSource.getCurrentUser()
    }

    override fun saveUser(user: User): OneOf<Failure, Boolean> = runBlocking {
        cacheSource.saveUser(user)
    }
}