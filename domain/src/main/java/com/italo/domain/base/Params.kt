package com.italo.domain.base

import com.italo.domain.model.User

class AccessTokenParams(val accessTokenString: String)
class UserParams(val user: User)
class LocationParams(val lat: Double, val lng: Double, val radius: Int)