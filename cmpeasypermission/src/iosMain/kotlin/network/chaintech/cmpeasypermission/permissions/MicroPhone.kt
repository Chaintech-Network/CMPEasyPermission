package network.chaintech.cmpeasypermission.permissions

import androidx.compose.runtime.Composable
import network.chaintech.cmpeasypermission.ui.DialogParams
import network.chaintech.cmpeasypermission.ui.MyAlertDialog
import network.chaintech.cmpeasypermission.openAppSettings
import platform.AVFAudio.AVAudioSession
import platform.AVFAudio.AVAudioSessionRecordPermissionDenied
import platform.AVFAudio.AVAudioSessionRecordPermissionGranted
import platform.AVFAudio.AVAudioSessionRecordPermissionUndetermined

@Composable
internal fun handleMicroPhoneAuthorizationStatus(
    deniedDialogParams: DialogParams?,
    onGranted: (Boolean) -> Unit,
    openSetting: Boolean
) {
    val status = AVAudioSession.sharedInstance().recordPermission
    when (status) {
        AVAudioSessionRecordPermissionGranted -> onGranted(true)
        AVAudioSessionRecordPermissionUndetermined -> requestMicroPhonePermission(onGranted)
        AVAudioSessionRecordPermissionDenied ->
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
}
private fun requestMicroPhonePermission(onGranted: (Boolean) -> Unit) {
    AVAudioSession.sharedInstance().requestRecordPermission { granted ->
        onGranted(granted)
    }
}
