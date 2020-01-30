package com.italo.mesachallenge.data.cache.source

import android.content.Context
import com.italo.domain.base.Failure
import com.italo.domain.base.OneOf
import com.italo.domain.model.User
import java.io.IOException

class UserCacheDataSourceImpl(private val context: Context) : UserCacheDataSource {

    override fun saveUser(user: User): OneOf<Failure, Boolean> {
        val sharedPref = context.getSharedPreferences(PREFERENCE_USER_KEY, Context.MODE_PRIVATE)
        return try {
            with (sharedPref.edit()) {
                putString(CURRENT_NAME_KEY, user.name)
                putString(CURRENT_PIC_URL_KEY, user.pictureUrl)
                commit()
            }
            OneOf.Success(true)
        } catch (e: IOException) {
            OneOf.Error(Failure.RetrieveCacheUserFailure())
        }
    }

    override fun getCurrentUser(): OneOf<Failure, User> {
        val sharedPref = context.getSharedPreferences(PREFERENCE_USER_KEY, Context.MODE_PRIVATE)
        val name = sharedPref.getString(CURRENT_NAME_KEY, "")
        val picUrl = sharedPref.getString(CURRENT_PIC_URL_KEY, "")

        return if (name != null && picUrl != null) {
            OneOf.Success(User(name, picUrl))
        } else {
            OneOf.Error(Failure.RetrieveCacheUserFailure())
        }
    }

    companion object {
        private const val PREFERENCE_USER_KEY = "com.italo.mesachallenge.PREFERENCE_USER_KEY"
        private const val CURRENT_NAME_KEY = "com.italo.mesachallenge.CURRENT_NAME_KEY"
        private const val CURRENT_PIC_URL_KEY = "com.italo.mesachallenge.CURRENT_PIC_URL_KEY"
    }
}