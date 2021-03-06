package com.pedrofr.sportsfinder.networking

sealed class Result<out T : Any>

data class Success<out T : Any>(val data: T): Result<T>()

data class Failure(val error: Throwable?): Result<Nothing>()

object Loading: Result<Nothing>()

object NoResults: Result<Nothing>()
