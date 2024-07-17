package com.spidex.safe

import android.content.Context
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class PermissionViewModel : ViewModel() {
    private val _currentPermissionIndex = MutableStateFlow(0)
    val currentPermissionIndex: StateFlow<Int> = _currentPermissionIndex.asStateFlow()

    private val _allPermissionsGranted = MutableStateFlow(false)
    val allPermissionsGranted: StateFlow<Boolean> = _allPermissionsGranted.asStateFlow()

    private val deniedPermissions = mutableSetOf<String>()

    private fun moveToNextPermission() {
        _currentPermissionIndex.value++
    }

    fun onPermissionResult(permission: String, isGranted: Boolean) {
        if (!isGranted) {
            deniedPermissions.add(permission)
        }

        if (currentPermissionIndex.value >= _visiblePermissionDialogQueue.value.size - 1) {
            _allPermissionsGranted.value = deniedPermissions.isEmpty()
            _visiblePermissionDialogQueue.value = emptyList()
            _currentPermissionIndex.value = 0
        } else {
            moveToNextPermission()
        }
    }

    fun dismissDialog() {
        _visiblePermissionDialogQueue.value = _visiblePermissionDialogQueue.value.toMutableList().also {
            it.removeAt(currentPermissionIndex.value)
        }
        _currentPermissionIndex.value--
    }

    private val _visiblePermissionDialogQueue = MutableStateFlow<List<String>>(emptyList())
    val visiblePermissionDialogQueue: StateFlow<List<String>> = _visiblePermissionDialogQueue.asStateFlow()

    fun permissionCheck(context: Context, permissions: List<String>) : Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    fun showPermissionDialog(permissions: List<String>) {
        _visiblePermissionDialogQueue.value = permissions
    }

//    fun permissionCheck(context: Activity): Boolean {
//
//    }
}