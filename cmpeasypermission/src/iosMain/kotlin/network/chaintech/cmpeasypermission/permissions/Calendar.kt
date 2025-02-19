package network.chaintech.cmpeasypermission.permissions

import androidx.compose.runtime.Composable
import network.chaintech.cmpeasypermission.ui.DialogParams
import network.chaintech.cmpeasypermission.ui.MyAlertDialog
import network.chaintech.cmpeasypermission.openAppSettings
import platform.EventKit.EKAuthorizationStatusAuthorized
import platform.EventKit.EKAuthorizationStatusDenied
import platform.EventKit.EKAuthorizationStatusNotDetermined
import platform.EventKit.EKAuthorizationStatusRestricted
import platform.EventKit.EKEntityType
import platform.EventKit.EKEventStore

@Composable
internal fun handleCalendarAuthorizationStatus(
    deniedDialogParams: DialogParams?,
    onGranted: (Boolean) -> Unit,
    openSetting: Boolean
) {
    val status = EKEventStore.authorizationStatusForEntityType(EKEntityType.EKEntityTypeEvent)
    when (status) {
        EKAuthorizationStatusAuthorized -> onGranted(true)
        EKAuthorizationStatusNotDetermined -> requestCalendarPermission(onGranted)
        EKAuthorizationStatusDenied,
        EKAuthorizationStatusRestricted -> if (openSetting && deniedDialogParams != null) {
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
            onGranted(false)
        }
    }
}

private fun requestCalendarPermission(onGranted: (Boolean) -> Unit) {
    val store = EKEventStore()
    store.requestAccessToEntityType(EKEntityType.EKEntityTypeEvent) { granted, _ ->
        onGranted(granted)
    }
}
