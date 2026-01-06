package com.example.data.datasource.remote

import android.content.Context
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class KakaoLoginDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun getKakaoAccessToken(): String = suspendCancellableCoroutine { continuation ->
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        continuation.resumeWithException(error)
                        return@loginWithKakaoTalk
                    }
                    loginWithWebView(continuation)
                } else if (token != null) {
                    continuation.resume(token.accessToken)
                }
            }
        } else {
            loginWithWebView(continuation)
        }
    }

    private fun loginWithWebView(continuation: CancellableContinuation<String>) {
        UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
            if (error != null) {
                continuation.resumeWithException(error)
            } else if (token != null) {
                continuation.resume(token.accessToken)
            }
        }
    }
}