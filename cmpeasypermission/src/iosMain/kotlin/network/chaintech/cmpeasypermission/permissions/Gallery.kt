package network.chaintech.cmpeasypermission.permissions

import androidx.compose.runtime.Composable
import network.chaintech.cmpeasypermission.ui.DialogParams
import network.chaintech.cmpeasypermission.ui.MyAlertDialog
import network.chaintech.cmpeasypermission.openAppSettings
import platform.Photos.PHAuthorizationStatusAuthorized
import platform.Photos.PHAuthorizationStatusDenied
import platform.Photos.PHAuthorizationStatusNotDetermined
import platform.Photos.PHAuthorizationStatusRestricted
import platform.Photos.PHPhotoLibrary

@Composable
internal fun handleGalleryAuthorizationStatus(
    deniedDialogParams: DialogParams?,
    onGranted: (Boolean) -> Unit,
    openSetting: Boolean
) {
    val status = PHPhotoLibrary.authorizationStatus()
    when (status) {
        PHAuthorizationStatusAuthorized -> onGranted(true)
        PHAuthorizationStatusNotDetermined -> requestGalleryPermission(onGranted)
        PHAuthorizationStatusDenied, PHAuthorizationStatusRestricted -> if (openSetting && deniedDialogParams != null) {
            MyAlertDialog(
                dialogParams = deniedDialogParams,
                onConfirmButtonClicked = {
                    onGranted(false)
                    openAppSettings()
                },
                onDismissButtonClicked = { onGranted(false) }
            )
        } else {
            onGranted(false)
        }

        else -> {
            requestGalleryPermission(onGranted)
        }
    }
}

private fun requestGalleryPermission(onGranted: (Boolean) -> Unit) {
    PHPhotoLibrary.requestAuthorization { status ->
        val granted = status == PHAuthorizationStatusAuthorized
        onGranted(granted)
    }
}
