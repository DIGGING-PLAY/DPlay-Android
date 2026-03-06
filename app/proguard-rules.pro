# 카카오 sdk 내부에 모델이 이름이 바뀌어 NoSuchFieldException 발생 방지
-keep class com.kakao.sdk.auth.model.** { *; }
-keep class com.kakao.sdk.common.model.** { *; }