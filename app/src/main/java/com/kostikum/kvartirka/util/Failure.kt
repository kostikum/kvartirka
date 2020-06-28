package com.kostikum.kvartirka.util

sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()

    abstract class FeatureFailure: Failure()
}
