package network.chaintech.cmpeasypermission.permissions

import androidx.compose.runtime.Composable
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExportObjCClass
import network.chaintech.cmpeasypermission.ui.DialogParams
import network.chaintech.cmpeasypermission.ui.MyAlertDialog
import network.chaintech.cmpeasypermission.openAppSettings
import platform.CoreBluetooth.CBCentralManager
import platform.CoreBluetooth.CBCentralManagerDelegateProtocol
import platform.CoreBluetooth.CBCentralManagerStatePoweredOff
import platform.CoreBluetooth.CBCentralManagerStatePoweredOn
import platform.CoreBluetooth.CBCentralManagerStateResetting
import platform.CoreBluetooth.CBCentralManagerStateUnauthorized
import platform.CoreBluetooth.CBCentralManagerStateUnknown
import platform.CoreBluetooth.CBCentralManagerStateUnsupported
import platform.CoreBluetooth.CBManager
import platform.CoreBluetooth.CBManagerAuthorizationAllowedAlways
import platform.CoreBluetooth.CBManagerAuthorizationDenied
import platform.CoreBluetooth.CBManagerAuthorizationNotDetermined
import platform.CoreBluetooth.CBManagerAuthorizationRestricted
import platform.darwin.NSObject


@OptIn(BetaInteropApi::class)
@ExportObjCClass
internal class BluetoothPermissionManager : NSObject(), CBCentralManagerDelegateProtocol {
    private var callBack: ((Boolean) -> Unit)? = null
    private var centralManager: CBCentralManager? = null
    init {
        centralManager = CBCentralManager(this, null)
    }

    @Composable
    fun handleBluetoothAuthorizationStatus(
        deniedDialogParams: DialogParams?,
        onGranted: (Boolean) -> Unit,
        openSetting: Boolean
    ) {

        when(CBManager.authorization) {
            CBManagerAuthorizationNotDetermined -> requestBluetoothPermission(onGranted)
            CBManagerAuthorizationDenied,
            CBManagerAuthorizationRestricted -> if (openSetting && deniedDialogParams != null) {
                MyAlertDialog(
                    dialogParams = deniedDialogParams,
                    onConfirmButtonClicked = {
                        onGranted(false)
                        callBack = onGranted
                        openAppSettings()
                    },
                    onDismissButtonClicked = { onGranted(false) }
                )
            } else {
                onGranted(false)
            }
            CBManagerAuthorizationAllowedAlways -> {
                callBack = onGranted
                onGranted(true)
            }
            else -> onGranted(false)
        }
    }
    private fun requestBluetoothPermission(onGranted: (Boolean) -> Unit) {
        callBack = onGranted
        centralManager?.scanForPeripheralsWithServices(null, null)
    }

    override fun centralManagerDidUpdateState(central: CBCentralManager) {
        when (central.state) {
            CBCentralManagerStateUnknown ->  callBack?.let { it(false) }
            CBCentralManagerStateResetting -> callBack?.let { it(false) }
            CBCentralManagerStateUnsupported -> callBack?.let { it(false) }
            CBCentralManagerStateUnauthorized -> callBack?.let { it(false) }
            CBCentralManagerStatePoweredOff -> callBack?.let { it(false) }
            CBCentralManagerStatePoweredOn -> callBack?.let { it(true) }
            else -> callBack?.let { it(false) }
        }
    }
}