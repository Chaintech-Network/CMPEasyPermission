package network.chaintech.cmpeasypermission.permissions

import androidx.compose.runtime.Composable
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExportObjCClass
import network.chaintech.cmpeasypermission.ui.DialogParams
import network.chaintech.cmpeasypermission.ui.MyAlertDialog
import network.chaintech.cmpeasypermission.openAppSettings
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorized
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusDenied
import platform.CoreLocation.kCLAuthorizationStatusNotDetermined
import platform.CoreLocation.kCLAuthorizationStatusRestricted
import platform.darwin.NSObject

@OptIn(BetaInteropApi::class)
@ExportObjCClass
internal class LocationManager : NSObject(), CLLocationManagerDelegateProtocol {
    private val locationManager = CLLocationManager()
    private var callBack: ((Boolean) -> Unit)? = null

    init {
        locationManager.delegate = this
    }

    private fun requestLocationAuthorization(onGranted: (Boolean) -> Unit) {
        callBack = onGranted
        locationManager.requestAlwaysAuthorization()
        locationManager.requestWhenInUseAuthorization()
    }

    @Composable
    fun handleLocationAuthorizationStatus(
        deniedDialogParams: DialogParams?,
        onGranted: (Boolean) -> Unit,
        openSetting: Boolean
    ) {
        val status = CLLocationManager.authorizationStatus()
        when (status) {
            kCLAuthorizationStatusAuthorized,
            kCLAuthorizationStatusAuthorizedAlways,
            kCLAuthorizationStatusAuthorizedWhenInUse -> onGranted(true)
            kCLAuthorizationStatusNotDetermined -> requestLocationAuthorization(onGranted)
            kCLAuthorizationStatusDenied,
            kCLAuthorizationStatusRestricted ->
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

    override fun locationManagerDidChangeAuthorization(manager: CLLocationManager) {
//        super.locationManagerDidChangeAuthorization(manager)
        val status = manager.authorizationStatus
        when (status) {
            kCLAuthorizationStatusAuthorized,
            kCLAuthorizationStatusAuthorizedAlways,
            kCLAuthorizationStatusAuthorizedWhenInUse -> callBack?.let { it(true) }
            kCLAuthorizationStatusNotDetermined -> callBack?.let { requestLocationAuthorization(it) }
            kCLAuthorizationStatusDenied,
            kCLAuthorizationStatusRestricted -> callBack?.let { it(false) }
            else -> callBack?.let { it(false) }
        }
    }
}

