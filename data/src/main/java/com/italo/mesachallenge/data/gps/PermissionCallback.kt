package com.italo.mesachallenge.data.gps

import com.italo.domain.base.Failure
import com.italo.domain.base.OneOf
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.BasePermissionListener
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

class PermissionCallback(private val task: Continuation<OneOf<Failure, Boolean>>) :
    BasePermissionListener() {

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        task.resume(OneOf.Success(true))
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        task.resume(OneOf.Error(Failure.PermissionDeniedFailure()))
    }
}