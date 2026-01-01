package com.example.network

import javax.inject.Qualifier

/**
 * DPlay 앱의 일반적인 비즈니스 로직을 위한 의존성 주입 한정자(Qualifier)입니다.
 *
 * 이 한정자가 붙은 `OkHttpClient`나 `Retrofit` 객체는 **Access Token을 헤더에 자동으로 포함하는 Interceptor**가 적용되어 있습니다.
 * 따라서 로그인이 완료된 후, 인증된 사용자만 접근할 수 있는 API(예: 음악 목록 조회, 댓글 작성, 마이페이지 등)를 호출할 때 사용합니다.
 *
 * - **특징**: `Authorization: Bearer {token}` 헤더 포함
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DPlay

/**
 * **인증(로그인, 회원가입, 토큰 갱신)** 관련 로직을 위한 의존성 주입 한정자(Qualifier)입니다.
 *
 * 이 한정자가 붙은 `OkHttpClient`나 `Retrofit` 객체는 **Access Token을 헤더에 포함하지 않는(순수한)** 상태입니다.
 *
 * - **특징**: `Authorization` 헤더 미포함
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Auth