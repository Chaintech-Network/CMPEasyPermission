package network.chaintech.cmpeasypermission.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import network.chaintech.cmpeasypermission.ui.DialogParams
import network.chaintech.cmpeasypermission.ui.MyAlertDialog
import network.chaintech.cmpeasypermission.openAppSettings
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNAuthorizationStatusAuthorized
import platform.UserNotifications.UNAuthorizationStatusDenied
import platform.UserNotifications.UNAuthorizationStatusNotDetermined
import platform.UserNotifications.UNUserNotificationCenter

@Composable
internal fun handleNotificationAuthorizationStatus(
    deniedDialogParams: DialogParams?,
    onGranted: (Boolean) -> Unit,
    openSetting: Boolean
) {
    var showSettings by remember { mutableStateOf(false) }

    UNUserNotificationCenter.currentNotificationCenter().getNotificationSettingsWithCompletionHandler {
        if (it == null) {
            onGranted(false)
        } else {
            when(it.authorizationStatus) {
                UNAuthorizationStatusAuthorized -> onGranted(true)
                UNAuthorizationStatusDenied ->
                    if (openSetting && deniedDialogParams != null) {
                        showSettings = true
                    } else {
                        onGranted(false)
                    }
                UNAuthorizationStatusNotDetermined -> requestNotificationPermission(onGranted)
                else -> onGranted(false)
            }
        }
    }

    if (showSettings) {
        deniedDialogParams?.let {
            MyAlertDialog(
                dialogParams = it,
                onConfirmButtonClicked = {
                    showSettings = false
                    onGranted(false)
                    openAppSettings()
                },
                onDismissButtonClicked = {
                    showSettings = false
                    onGranted(false)
                }
            )
        }
    }
}

// Function to request notification permission
private fun requestNotificationPermission(onGranted: (Boolean) -> Unit) {
    UNUserNotificationCenter.currentNotificationCenter().requestAuthorizationWithOptions(
        options = UNAuthorizationOptionAlert or UNAuthorizationOptionSound or UNAuthorizationOptionBadge,
        completionHandler = { granted, _ ->
            onGranted(granted)
        })
}