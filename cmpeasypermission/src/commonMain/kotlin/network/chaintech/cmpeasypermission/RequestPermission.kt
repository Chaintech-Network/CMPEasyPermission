package network.chaintech.cmpeasypermission

import androidx.compose.runtime.Composable

@Composable
expect fun RequestPermission(
    permission: PermissionState,
    openSetting: Boolean,
    deniedDialogTitle: String,
    deniedDialogDesc: String,
    isGranted: (Boolean) -> Unit,
)