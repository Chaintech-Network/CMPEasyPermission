package network.chaintech.cmpeasypermission.permissions

import androidx.compose.runtime.Composable
import network.chaintech.cmpeasypermission.ui.DialogParams
import network.chaintech.cmpeasypermission.ui.MyAlertDialog
import network.chaintech.cmpeasypermission.openAppSettings
import platform.Contacts.CNAuthorizationStatusAuthorized
import platform.Contacts.CNAuthorizationStatusDenied
import platform.Contacts.CNAuthorizationStatusNotDetermined
import platform.Contacts.CNAuthorizationStatusRestricted
import platform.Contacts.CNContactStore
import platform.Contacts.CNEntityType

@Composable
internal fun handleContactAuthorizationStatus(
    deniedDialogParams: DialogParams?,
    onGranted: (Boolean) -> Unit,
    openSetting: Boolean
) {
    val status = CNContactStore.authorizationStatusForEntityType(CNEntityType.CNEntityTypeContacts)
    return when (status) {
        CNAuthorizationStatusAuthorized -> onGranted(true)
        CNAuthorizationStatusDenied,
        CNAuthorizationStatusRestricted -> if (openSetting && deniedDialogParams != null) {
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
        CNAuthorizationStatusNotDetermined -> requestContactPermission(onGranted)
        else -> onGranted(false)
    }
}

private fun requestContactPermission(onGranted: (Boolean) -> Unit) {
    val store = CNContactStore()
    store.requestAccessForEntityType(CNEntityType.CNEntityTypeContacts) { granted, _ ->
        onGranted(granted)
    }
}
