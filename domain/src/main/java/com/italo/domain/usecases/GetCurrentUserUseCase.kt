package com.italo.domain.usecases

import com.italo.domain.base.Failure
import com.italo.domain.base.OneOf
import com.italo.domain.base.UseCase
import com.italo.domain.model.User
import com.italo.domain.repository.UserRepository

class GetCurrentUserUseCase(private val userRepository: UserRepository) :
    UseCase<User, UseCase.NoParams>() {

    override suspend fun run(params: NoParams): OneOf<Failure, User> = userRepository.getUser()
}