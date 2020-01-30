package com.italo.mesachallenge.data.cache.source

import com.italo.domain.base.Failure
import com.italo.domain.base.OneOf
import com.italo.domain.model.User

interface UserCacheDataSource {
    fun saveUser(user: User): OneOf<Failure, Boolean>
    fun getCurrentUser(): OneOf<Failure, User>
}