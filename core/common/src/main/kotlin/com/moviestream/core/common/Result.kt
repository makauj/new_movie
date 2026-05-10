package com.moviestream.core.common

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    data object Loading : Result<Nothing>()

    fun getOrNull(): T? = when (this) {
        is Success -> data
        is Error -> null
        is Loading -> null
    }

    fun exceptionOrNull(): Exception? = when (this) {
        is Error -> exception
        else -> null
    }

    fun isSuccess() = this is Success
    fun isError() = this is Error
    fun isLoading() = this is Loading
}

sealed class UiState<out T> {
    data object Idle : UiState<Nothing>()
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()

    fun getDataOrNull(): T? = when (this) {
        is Success -> data
        else -> null
    }
}
