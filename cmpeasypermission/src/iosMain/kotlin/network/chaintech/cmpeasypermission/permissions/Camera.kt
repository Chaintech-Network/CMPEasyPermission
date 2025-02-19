package network.chaintech.cmpeasypermission.permissions

import androidx.compose.runtime.Composable
import network.chaintech.cmpeasypermission.ui.DialogParams
import network.chaintech.cmpeasypermission.ui.MyAlertDialog
import network.chaintech.cmpeasypermission.openAppSettings
import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVAuthorizationStatusDenied
import platform.AVFoundation.AVAuthorizationStatusNotDetermined
import platform.AVFoundation.AVAuthorizationStatusRestricted
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.authorizationStatusForMediaType
import platform.AVFoundation.requestAccessForMediaType
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerSourceType

@Composable
internal fun handleCameraAuthorizationStatus(
    deniedDialogParams: DialogParams?,
    onGranted: (Boolean) -> Unit,
    openSetting: Boolean
) {
    if (UIImagePickerController.isSourceTypeAvailable(UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera)) {
        val status = AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)
        when (status) {
            AVAuthorizationStatusAuthorized -> onGranted(true)
            AVAuthorizationStatusNotDetermined -> requestCameraPermission(onGranted)
            AVAuthorizationStatusDenied, AVAuthorizationStatusRestricted ->
                if (openSetting && deniedDialogParams != null) {
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

            else -> onGranted(false)
        }
    } else {
        onGranted(false)
    }
}

// Function to request camera permission
private fun requestCameraPermission(onGranted: (Boolean) -> Unit) {
    AVCaptureDevice.requestAccessForMediaType(AVMediaTypeVideo) { granted ->
        onGranted(granted)
    }
}