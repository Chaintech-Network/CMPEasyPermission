# CMPEasyPermission

CMPEasyPermission is a permission library for Compose Multiplatform, supporting both Android and iOS.

![CMPEasyPermission](https://github.com/user-attachments/assets/4ac153b8-af49-4270-aa8e-2649d85d9ed1)

## Installation

1. Add the dependency to your `build.gradle.kts` file:

```
commonMain.dependencies {
    implementation("network.chaintech:cmp-easy-permission:1.0.1")
}
```

## Usage

- Android: Include this at root level in your `AndroidManifest.xml`:

```
<uses-feature android:name="android.hardware.camera"/>
<uses-feature android:name="android.hardware.camera.autofocus"/>
<uses-permission android:name="android.permission.CAMERA"/>
```

- iOS: Add below key to the `Info.plist` in your xcode project:

```
<key>NSCameraUsageDescription</key><string>$(PRODUCT_NAME) camera description.</string>
```

- Example:

```kotlin

var openCamera by remember { mutableStateOf(false) }
var isGrantedCamera by remember { mutableStateOf<Boolean?>(null) }

if (openCamera) {
    RequestPermission(
        permission = PermissionState.CAMERA,
        openSetting = true,
        deniedDialogParams = DialogParams(
            titleStr = "App requires access to your camera",
            messageStr = "You can enable this permission in the settings",
        ),
        isGranted = { isGranted ->
            isGrantedCamera = isGranted
            openCamera = false
        },
    )
}

Box(
    modifier = Modifier
        .clickable {
            openCamera = true
        }
) {
    // your content
}
```

`permission`: Represents requested permission.

`openSetting`: A flag indicating whether to open the device settings if the permission is denied.

`deniedDialogTitle`: String: The title text for the dialog that appears if the permission is denied.

`deniedDialogDesc`: String: The description text for the dialog that appears if the permission is denied.

`isGranted`: A callback function that is invoked with a boolean value indicating whether the permission was granted or not.

## Demo

![CMPEasyPermission](https://github.com/user-attachments/assets/54481052-5ed3-410e-81d8-07f30720351d)

- For Demo [Checkout This Class](https://github.com/Chaintech-Network/CMPEasyPermission/blob/main/composeApp/src/commonMain/kotlin/network/chaintech/cmpeasyPpermissiondemo/App.kt)

- [Medium Article](https://medium.com/mobile-innovation-network/cmpeasypermission-a-compose-multiplatform-permission-library-for-android-and-ios-c5ae541b886f) for detailed explaination.

- Connect us on [LinkedIn](https://www.linkedin.com/showcase/mobile-innovation-network)
