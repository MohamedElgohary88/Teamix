package com.chocolate.repository.datastore.remote

interface AuthenticationDataSource {
    suspend fun registerUser(email: String, password: String): String?

    suspend fun loginUser(email: String, password: String)

    fun getCurrentUserId(): String?

    fun isUserLoggedIn(): Boolean

    fun logoutUser()

    suspend fun isUserSignedIn(email: String): Boolean

}