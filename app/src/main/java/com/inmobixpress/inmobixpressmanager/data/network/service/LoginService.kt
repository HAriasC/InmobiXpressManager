package com.inmobixpress.inmobixpressmanager.data.network.service

import com.inmobixpress.inmobixpressmanager.data.network.model.NetworkResult
import com.inmobixpress.inmobixpressmanager.data.network.model.User
import kotlinx.coroutines.flow.Flow

interface LoginService {
    fun login(username: String, password: String): Flow<NetworkResult<User>>
}