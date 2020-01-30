package com.italo.domain.repository

import com.italo.domain.base.Failure
import com.italo.domain.base.OneOf
import com.italo.domain.model.User

interface UserRepository {
    fun getUser(): OneOf<Failure, User>
    fun saveUser(user: User): OneOf<Failure, Boolean>
}