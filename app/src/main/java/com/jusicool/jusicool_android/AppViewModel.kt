package com.jusicool.jusicool_android

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jusicool.network.util.BasicCookieJar
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    cookieJar: BasicCookieJar,
) : ViewModel() {

    private val _appLoginState = mutableStateOf<AppLoginState>(AppLoginState.Loading)
    val appLoginState: State<AppLoginState> = _appLoginState

    init {
        _appLoginState.value = if (cookieJar.hasAuthCookies("stage-api.jusicool.shop")) {
            AppLoginState.Success
        } else {
            AppLoginState.Fail
        }
    }
}

sealed interface AppLoginState {
    object Loading : AppLoginState
    object Success : AppLoginState
    object Fail : AppLoginState
}
