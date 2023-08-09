package com.chocolate.repository.repository

import com.chocolate.entities.NetworkException
import com.chocolate.entities.NoConnectionException
import com.chocolate.entities.RateLimitExceededException
import com.chocolate.entities.TeamixException
import com.chocolate.entities.UnAuthorizedException
import com.chocolate.entities.UnknownException
import com.chocolate.entities.UserDeactivatedException
import com.chocolate.repository.utils.HttpStatusCodes
import retrofit2.Response
import java.net.UnknownHostException

abstract class BaseRepository {
    protected suspend fun <T> wrapApiCall(call: suspend () -> Response<T>): T {
        return try {
            val result = call()

            when (result.code()) {
                HttpStatusCodes.BAD_REQUEST.code -> throw NetworkException(result.message())
                HttpStatusCodes.UNAUTHORIZED.code -> throw UnAuthorizedException(result.message())
                HttpStatusCodes.USER_DEACTIVATED.code -> throw UserDeactivatedException(result.message())
                HttpStatusCodes.TOO_MANY_REQUESTS.code -> throw RateLimitExceededException(result.message())
                HttpStatusCodes.NO_CONNECTION.code -> throw NoConnectionException(result.message())

                else -> result.body() ?: throw UnknownException(result.message())
            }

        } catch (exception: UnknownHostException) {
            throw UnknownException(exception.message.toString())
        } catch (exception: TeamixException) {
            throw UnknownException(exception.message.toString())
        }
    }

}