package network.chaintech.cmpeasypermission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import network.chaintech.cmpeasypermission.component.RequestPermissions
import network.chaintech.cmpeasypermission.ui.DialogParams

var storagePermissions = listOf(
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.READ_EXTERNAL_STORAGE
)

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
var storagePermissions33 = listOf(
    Manifest.permission.READ_MEDIA_IMAGES,
    Manifest.permission.READ_MEDIA_AUDIO,
    Manifest.permission.READ_MEDIA_VIDEO
)

@Composable
actual fun RequestPermission(
    permission: PermissionState,
    openSetting: Boolean,
    deniedDialogTitle: String,
    deniedDialogDesc: String,
    isGranted: (Boolean) -> Unit,
) {

    val permissionsList = mutableListOf<String>()

    when (permission) {
        PermissionState.BLUETOOTH -> {
            handleBluetoothPermission(permissionsList, LocalContext.current, isGranted)
        }

        PermissionState.STORAGE -> {
            handleStoragePermission(permissionsList)
        }

        PermissionState.ACTIVITY_RECOGNITION,
        PermissionState.ACCESS_MEDIA_LOCATION -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                permissionsList.add(permission.value)
            }
        }

        PermissionState.POST_NOTIFICATIONS -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionsList.add(permission.value)
            }
        }

        PermissionState.ANSWER_PHONE_CALLS,
        PermissionState.READ_PHONE_NUMBERS -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                permissionsList.add(permission.value)
            }
        }

        else -> {
            permissionsList.add(permission.value)
        }
    }

    if (permissionsList.isEmpty()) {
        isGranted(true)
        return
    }

    RequestPermissions(
        permissions = permissionsList,
        openSetting = openSetting,
        deniedDialogParams = DialogParams(
            titleStr = deniedDialogTitle,
            messageStr = deniedDialogDesc,
        ),
        isGranted = {
            isGranted(true)
        },
        isDenied = {
            isGranted(false)
        },
        onDone = {
            isGranted(false)
        }
    )
}
// Helper function for handling Bluetooth permissions
private fun handleBluetoothPermission(permissionsList: MutableList<String>, context: Context, isGranted: (Boolean) -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val isBluetoothGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED
        val isScanGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED

        if (isBluetoothGranted && isScanGranted) {
            isGranted(true)
        } else {
            permissionsList.add(Manifest.permission.BLUETOOTH_CONNECT)
            permissionsList.add(Manifest.permission.BLUETOOTH_SCAN)
        }
    } else {
        val isBluetoothAdminGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED
        if (isBluetoothAdminGranted) {
            isGranted(true)
        } else {
            permissionsList.add(Manifest.permission.BLUETOOTH_ADMIN)
        }
    }
}

// Helper function for handling storage permissions
private fun handleStoragePermission(permissionsList: MutableList<String>) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        permissionsList.addAll(storagePermissions33)
    } else {
        permissionsList.addAll(storagePermissions)
    }
}