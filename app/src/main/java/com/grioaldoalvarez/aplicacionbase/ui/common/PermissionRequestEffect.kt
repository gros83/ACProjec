package com.grioaldoalvarez.aplicacionbase.ui.common

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun PermissionRequestEffect(permission:String, onResult: (Boolean) -> Unit) {
    val permissionLauncher = 
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
            onResult(it)
        }
    // LaunchEffect lo que permite es lanzar solo una vez el permissionLauncher
    LaunchedEffect(Unit) {
        permissionLauncher.launch(permission)
    }
}