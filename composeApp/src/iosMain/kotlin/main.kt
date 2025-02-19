import androidx.compose.ui.window.ComposeUIViewController
import network.chaintech.cmpeasypermissiondemo.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController(configure = { enforceStrictPlistSanityCheck = false }) { App() }
