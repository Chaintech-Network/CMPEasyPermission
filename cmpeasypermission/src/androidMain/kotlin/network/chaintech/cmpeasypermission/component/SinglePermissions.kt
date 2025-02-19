package network.chaintech.cmpeasypermission.component

import android.annotation.SuppressLint
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
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import network.chaintech.cmpeasypermission.findActivity
import network.chaintech.cmpeasypermission.ui.DialogParams
import network.chaintech.cmpeasypermission.ui.MyAlertDialog

@SuppressLint("PermissionLaunchedDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun SinglePermission(
    permission: String,
    openSetting: Boolean,
    deniedDialogParams: DialogParams? = null,
    isGranted: () -> Unit,
    isDenied: () -> Unit,
    onDone: () -> Unit,
) {
    val context = LocalContext.current
    val activity = context.findActivity() ?: return
    val shouldShowDeniedDialog = deniedDialogParams != null
    var openDeniedDialog by rememberSaveable { mutableStateOf(false) }
    var firstTime by rememberSaveable { mutableStateOf(true) }

    val permissionState = rememberPermissionState(
        permission = permission,
        onPermissionResult = { isPermissionGranted ->
            val permissionPermanentlyDenied = !ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                permission
            ) && !isPermissionGranted
            when {
                permissionPermanentlyDenied && shouldShowDeniedDialog -> {
                    if (openSetting) openDeniedDialog = true else isDenied()
                }
                isPermissionGranted -> isGranted()
                else -> isDenied()
            }
        })

    if (openDeniedDialog && openSetting) {
        ShowDialog(deniedDialogParams!!,
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
            })
    }

    LaunchedEffect(firstTime) {
        if (firstTime) {
            firstTime = false
            if (permissionState.status.isGranted) isGranted()
            else permissionState.launchPermissionRequest()
        }
    }
}

@Composable
fun ShowDialog(
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