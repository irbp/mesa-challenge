package com.italo.domain.usecases

import com.italo.domain.base.Failure
import com.italo.domain.base.OneOf
import com.italo.domain.base.UseCase
import com.italo.domain.base.UserParams
import com.italo.domain.repository.UserRepository

class SaveCurrentUserUseCase(private val userRepository: UserRepository) :
    UseCase<Boolean, UserParams>() {
    override suspend fun run(params: UserParams): OneOf<Failure, Boolean> =
        userRepository.saveUser(params.user)
}