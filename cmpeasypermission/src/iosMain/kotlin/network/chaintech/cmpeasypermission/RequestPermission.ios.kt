package network.chaintech.cmpeasypermission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import network.chaintech.cmpeasypermission.permissions.BluetoothPermissionManager
import network.chaintech.cmpeasypermission.permissions.LocationManager
import network.chaintech.cmpeasypermission.permissions.handleCalendarAuthorizationStatus
import network.chaintech.cmpeasypermission.permissions.handleCameraAuthorizationStatus
import network.chaintech.cmpeasypermission.permissions.handleContactAuthorizationStatus
import network.chaintech.cmpeasypermission.permissions.handleGalleryAuthorizationStatus
import network.chaintech.cmpeasypermission.permissions.handleMicroPhoneAuthorizationStatus
import network.chaintech.cmpeasypermission.permissions.handleNotificationAuthorizationStatus
import network.chaintech.cmpeasypermission.ui.DialogParams

@Composable
actual fun RequestPermission(
    permission: PermissionState,
    openSetting: Boolean,
    deniedDialogTitle: String,
    deniedDialogDesc: String,
    isGranted: (Boolean) -> Unit,
) {
    val deniedDialogParams = remember { DialogParams(deniedDialogTitle, deniedDialogDesc) }

    when (permission) {
        PermissionState.CAMERA -> {
            handleCameraAuthorizationStatus(deniedDialogParams, isGranted, openSetting)
        }

        PermissionState.RECORD_AUDIO -> {
            handleMicroPhoneAuthorizationStatus(deniedDialogParams, isGranted, openSetting)
        }

        PermissionState.ACCESS_FINE_LOCATION, PermissionState.ACCESS_COARSE_LOCATION -> {
            LocationManager().handleLocationAuthorizationStatus(
                deniedDialogParams,
                isGranted,
                openSetting
            )
        }

        PermissionState.WRITE_CONTACTS, PermissionState.READ_CONTACTS -> {
            handleContactAuthorizationStatus(deniedDialogParams, isGranted, openSetting)
        }

        PermissionState.READ_CALENDAR, PermissionState.WRITE_CALENDAR -> {
            handleCalendarAuthorizationStatus(deniedDialogParams, isGranted, openSetting)
        }

        PermissionState.POST_NOTIFICATIONS -> {
            handleNotificationAuthorizationStatus(deniedDialogParams, isGranted, openSetting)
        }

        PermissionState.BLUETOOTH -> {
            BluetoothPermissionManager().handleBluetoothAuthorizationStatus(
                deniedDialogParams,
                isGranted,
                openSetting
            )
        }

        PermissionState.STORAGE -> {
            handleGalleryAuthorizationStatus(deniedDialogParams, isGranted, openSetting)
        }

        else -> {
            isGranted(true)
        }
    }
}