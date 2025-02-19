package network.chaintech.cmpeasypermission.component

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import network.chaintech.cmpeasypermission.findActivity
import network.chaintech.cmpeasypermission.ui.DialogParams
import network.chaintech.cmpeasypermission.ui.MyAlertDialog

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun MultiplePermissions(
    permissions: List<String>,
    openSetting: Boolean,
    deniedDialogParams: DialogParams? = null,
    isGranted: () -> Unit,
    isDenied: () -> Unit,
    onDone: () -> Unit
) {
    val context = LocalContext.current
    val activity = context.findActivity() ?: return
    var openDeniedDialog by rememberSaveable { mutableStateOf(false) }

    val multiplePermissionState = rememberMultiplePermissionsState(
        permissions = permissions,
        onPermissionsResult = { permissionResults ->
            val permissionsPermanentlyDenied = permissionResults.any { (permission, granted) ->
                !granted && !ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
            }

            when {
                permissionsPermanentlyDenied && deniedDialogParams != null -> {
                    if (openSetting) openDeniedDialog = true else isDenied()
                }
                permissionResults.all { it.value } -> isGranted()
                else -> isDenied()
            }
        }
    )

    if (openDeniedDialog && openSetting) {
        ShowDialogForMultiplePermission(
            dialogParams = deniedDialogParams!!,
            onConfirmButtonClicked = {
                activity
                    .startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    })
                openDeniedDialog = false
                onDone()
            },
            onDismiss = {
                openDeniedDialog = false
                onDone()
            }
        )
    }

    LaunchedEffect(true) {
        if (!multiplePermissionState.allPermissionsGranted) {
            multiplePermissionState.launchMultiplePermissionRequest()
        } else {
            isGranted()
        }
    }
}

@Composable
private fun ShowDialogForMultiplePermission(
    dialogParams: DialogParams,
    onConfirmButtonClicked: () -> Unit,
    onDismiss: () -> Unit
) {
    MyAlertDialog(
        dialogParams = dialogParams,
        onConfirmButtonClicked = onConfirmButtonClicked,
        onDismissButtonClicked = onDismiss
    )
}