package network.chaintech.cmpeasypermission.component

import androidx.compose.runtime.*
import network.chaintech.cmpeasypermission.ui.DialogParams

@Composable
internal fun RequestPermissions(
    permissions: List<String>,
    openSetting: Boolean,
    deniedDialogParams: DialogParams? = null,
    isGranted: () -> Unit,
    isDenied: () -> Unit,
    onDone: () -> Unit
) {
    if (permissions.isEmpty()) throw Exception("The list of permissions is empty")

    val showSinglePermissionRequest = permissions.size == 1
    if (showSinglePermissionRequest) {
        SinglePermission(
            permission = permissions[0],
            openSetting = openSetting,
            deniedDialogParams = deniedDialogParams,
            isGranted = isGranted,
            isDenied = isDenied,
            onDone = onDone
        )
    } else {
        MultiplePermissions(
            permissions = permissions,
            openSetting = openSetting,
            deniedDialogParams = deniedDialogParams,
            isGranted = isGranted,
            isDenied = isDenied,
            onDone = onDone
        )
    }
}


