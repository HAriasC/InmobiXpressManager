package com.inmobixpress.inmobixpressmanager.data.network.implement

import android.util.Log
import com.inmobixpress.inmobixpressmanager.data.network.model.NetworkResult
import com.inmobixpress.inmobixpressmanager.data.network.model.User
import com.inmobixpress.inmobixpressmanager.data.network.service.LoginService
import com.inmobixpress.inmobixpressmanager.data.network.utils.toResult
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginServiceImpl @Inject constructor(private val httpClient: HttpClient): LoginService {

    override fun login(username: String, password: String): Flow<NetworkResult<User>> = flow {
        emit(NetworkResult.Loading())
        val response = httpClient.post(urlString = "/login") {
            parameter("username", username)
            parameter("password", password)
        }.toResult<User>()
        Log.e("REPS", response.toString())
        emit(response)
    }
}