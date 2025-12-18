package com.example.ui.handler

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

class PermissionHandler(
    private val context: Context,
    private val launcher: ManagedActivityResultLauncher<String, Boolean>,
    private val onResult: (Boolean) -> Unit
) {
    fun request(permission: String) {
        val isGranted = ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED

        if (isGranted) {
            onResult(true)
        } else {
            launcher.launch(permission)
        }
    }

    fun requestIf(permission: String, condition: Boolean) {
        if (condition) {
            request(permission)
        } else {
            onResult(true)
        }
    }
}

@Composable
fun rememberPermissionHandler(
    onResult: (Boolean) -> Unit
): PermissionHandler {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = onResult
    )

    return remember(context, launcher, onResult) {
        PermissionHandler(context, launcher, onResult)
    }
}