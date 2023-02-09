package com.lrp.domain.utils

import android.annotation.SuppressLint
import android.util.Log
import java.util.concurrent.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.scan

@SuppressLint("MatchingDeclarationName")
sealed class ResultCustomFlow<T> {
    companion object {
        fun <T> suspended(isLoadingNeeded: Boolean = false, block: suspend () -> T): Flow<ResultCustomFlow<T>> = flow {
            if (isLoadingNeeded) {
                emit(Loading())
            }

            // Try to load the value.
            val terminalEvent = try {
                Success(block())
            } catch (e: Throwable) {
                if (e is Error || e is CancellationException) throw e
                Failure<T>(e)
            }
            emit(terminalEvent)
        }
    }

    data class Failure<T>(
        val exception: Throwable
    ) : ResultCustomFlow<T>() {
        override fun equals(other: Any?): Boolean =
            other is Failure<*> && exception == other.exception

        override fun hashCode(): Int = exception.hashCode()
    }

    data class Loading<T>(
        /**
         * A progress or `null` if the progress is
         * not supported.
         */
        val progress: Float? = null,
        val lastTerminalState: ResultCustomFlow<T>? = null
    ) : ResultCustomFlow<T>()

    data class Success<T>(
        val data: T
    ) : ResultCustomFlow<T>()
}

fun <T> ResultCustomFlow<T>.preferTerminalState() =
    when (this) {
        is ResultCustomFlow.Loading<T> -> lastTerminalState ?: this
        else -> this
    }

fun <T> ResultCustomFlow<T>.orNull() =
    when (this) {
        is ResultCustomFlow.Success<T> -> data
        else -> null
    }

fun <T, R> ResultCustomFlow<T>.fold(
    ifEmpty: () -> R,
    ifSome: (T) -> R,
): R = when (this) {
    is ResultCustomFlow.Success<T> -> ifSome(data)
    is ResultCustomFlow.Loading<T> -> lastTerminalState?.fold(ifEmpty, ifSome)
        ?: ifEmpty()
    is ResultCustomFlow.Failure<T> -> ifEmpty()
}

fun <T, R> ResultCustomFlow<T>.ap(ff: ResultCustomFlow<(T) -> R>) =
    flatMap { t ->
        ff.map { it(t) }
    }

inline fun <T, R> ResultCustomFlow<T>.map(transform: (T) -> R) =
    flatMap {
        ResultCustomFlow.Success(transform(it))
    }

inline fun <T, R> ResultCustomFlow<T>.flatMap(transform: (T) -> ResultCustomFlow<R>) =
    when (this) {
        is ResultCustomFlow.Failure<T> -> ResultCustomFlow.Failure(exception)
        is ResultCustomFlow.Loading<T> -> ResultCustomFlow.Loading(progress)
        is ResultCustomFlow.Success<T> -> transform(data)
    }

inline fun <T> ResultCustomFlow<T>.mapLeft(transform: (Throwable) -> T) =
    flatMapLeft {
        ResultCustomFlow.Success(transform(it))
    }

inline fun <T> ResultCustomFlow<T>.flatMapLeft(transform: (Throwable) -> ResultCustomFlow<T>) =
    when (this) {
        is ResultCustomFlow.Failure<T> -> transform(exception)
        is ResultCustomFlow.Loading<T> -> this
        is ResultCustomFlow.Success<T> -> this
    }

@ExperimentalCoroutinesApi
fun <T> Flow<ResultCustomFlow<T>>.withTerminalStateInLoading() = this
    .scan(null as ResultCustomFlow<T>?) { old, new ->
        when (new) {
            is ResultCustomFlow.Loading<T> -> {
                val lastTerminalState = when (old) {
                    null,
                    is ResultCustomFlow.Failure<T>,
                    is ResultCustomFlow.Success<T>,
                    -> old
                    is ResultCustomFlow.Loading<T> -> old.lastTerminalState
                }
                if (lastTerminalState !== new.lastTerminalState) {
                    new.copy(lastTerminalState = lastTerminalState)
                } else {
                    new
                }
            }
            else -> new
        }
    }
    .filterNotNull()

fun <T> Flow<T>.handleErrors(): Flow<T> =
    catch { e -> Log.e("EXCEPTION", e.printStackTrace().toString()) }
