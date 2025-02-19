package network.chaintech.cmpeasypermission

import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString

internal fun openAppSettings() {
    val settingsUrl = NSURL(string = UIApplicationOpenSettingsURLString)
    UIApplication.sharedApplication().openURL(settingsUrl)
}