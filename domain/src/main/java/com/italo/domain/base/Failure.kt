package com.italo.domain.base

sealed class Failure {
    class PermissionDeniedFailure : Failure()
    class GpsDisabled : Failure()
    class CancelFailure : Failure()
    class RetrieveCacheUserFailure : Failure()
    class SaveCacheUserFailure : Failure()
    class RetrieveNearbyFailure: Failure()
    class ConnectionFailure: Failure()
    class PlaceCacheFailure: Failure()
}