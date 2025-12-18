package com.example.ui.handler

import android.app.Activity
import android.content.Context
import android.widget.Toast
import timber.log.Timber

class AppTerminationHandler(private val context: Context) {
    private var backPressedTime: Long = 0
    private val finishIntervalTime: Long = 2000 // 2초

    fun onBackPress() {
        val currentTime = System.currentTimeMillis()
        val intervalTime = currentTime - backPressedTime
        Timber.d("intervalTime: $intervalTime")

        if (intervalTime in 0..finishIntervalTime) {
            // 2초 내에 다시 눌렀으면 앱 종료
            (context as? Activity)?.finish()
        } else {
            // 처음 눌렀거나 시간이 지났으면 Toast 표시
            backPressedTime = currentTime
            Toast.makeText(context, "'뒤로' 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }
}