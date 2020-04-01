package co.icanteach.app.coronatracker.core

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

sealed class Resource<out T> {
    class Success<T>(val data: T) : Resource<T>()
    class Error(val exception: Throwable) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}

inline fun <T, R> Resource<T>.map(transform: (T) -> R): Resource<R> {
    return when (this) {
        is Resource.Success -> Resource.Success(transform(data))
        is Resource.Error -> Resource.Error(exception)
        is Resource.Loading -> Resource.Loading
    }
}

inline fun <T1, T2, R> Resource<T1>.combine(
    resource: Resource<T2>,
    transform: (T1, T2) -> R
): Resource<R> {
    return when {
        this is Resource.Success && resource is Resource.Success -> {
            Resource.Success(transform(data, resource.data))
        }
        this is Resource.Error -> Resource.Error(exception)
        resource is Resource.Error -> Resource.Error(resource.exception)
        else -> Resource.Loading
    }
}

inline fun <T, R> Flow<Resource<T>>.mapResource(crossinline transform: suspend (T) -> R): Flow<Resource<R>> {
    return map { resource ->
        resource.map { transform(it) }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
inline fun <T1, T2, R> Flow<Resource<T1>>.combineResource(
    flow: Flow<Resource<T2>>,
    crossinline transform: suspend (T1, T2) -> R
): Flow<Resource<R>> {
    return combine(flow) { resource1, resource2 ->
        resource1.combine(resource2) { data1, data2 ->
            transform(data1, data2)
        }
    }
}