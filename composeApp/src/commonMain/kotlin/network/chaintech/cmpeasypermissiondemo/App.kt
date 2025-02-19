package network.chaintech.cmpeasypermissiondemo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cmpeasypermissiondemo.composeapp.generated.resources.*
import network.chaintech.cmpeasypermissiondemo.theme.AppTheme
import network.chaintech.cmpeasypermissiondemo.theme.ColorPalette
import network.chaintech.cmpeasypermission.PermissionState
import network.chaintech.cmpeasypermission.RequestPermission
import org.jetbrains.compose.resources.painterResource

@Composable
fun App() = AppTheme {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {
        TooltipViewExample()
    }
}

@Composable
fun TooltipViewExample() {
    Box(
        modifier = Modifier
            .background(Color(0xFF0E121A))
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        Scaffold(
            containerColor = Color(0xFF0E121A),
            modifier = Modifier
                .fillMaxSize(),
            topBar = { },
            floatingActionButton = { },
            bottomBar = { }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TopWelcomeView()

                    Spacer(modifier = Modifier.height(25.dp))

                    ButtonGridView()

                    Spacer(modifier = Modifier.height(25.dp))

                    MorePermissionView()

                    Spacer(
                        modifier = Modifier
                            .height(105.dp)
                            .windowInsetsPadding(WindowInsets.safeDrawing)
                    )
                }
            }
        }
    }
}

@Composable
fun TopWelcomeView() {
    Column(
        modifier = Modifier
            .padding(top = 48.dp)
            .padding(horizontal = 15.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Welcome User",
            color = ColorPalette.textPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Permission Library",
                color = ColorPalette.white,
                fontSize = 35.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
                modifier = Modifier
            )

            Image(
                painter = painterResource(Res.drawable.ic_top_right_arrow),
                contentDescription = Res.drawable.ic_top_right_arrow.toString(),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(30.dp)
                    .height(23.dp)
            )
        }
    }
}

@Composable
fun ButtonGridView() {
    var openStorage by remember { mutableStateOf(false) }
    var isGrantedStorage by remember { mutableStateOf<Boolean?>(null) }

    var openContact by remember { mutableStateOf(false) }
    var isGrantedContact by remember { mutableStateOf<Boolean?>(null) }

    var openNotification by remember { mutableStateOf(false) }
    var isGrantedNotification by remember { mutableStateOf<Boolean?>(null) }

    var openCamera by remember { mutableStateOf(false) }
    var isGrantedCamera by remember { mutableStateOf<Boolean?>(null) }

    var openMicrophone by remember { mutableStateOf(false) }
    var isGrantedMicrophone by remember { mutableStateOf<Boolean?>(null) }

    var openLocation by remember { mutableStateOf(false) }
    var isGrantedLocation by remember { mutableStateOf<Boolean?>(null) }

    if (openStorage) {
        RequestPermission(
            permission = PermissionState.STORAGE,
            openSetting = true,
            deniedDialogTitle = "App requires access to your storage",
            deniedDialogDesc = "You can enable this permission in the settings",
            isGranted = { isGranted ->
                isGrantedStorage = isGranted
                openStorage = false
            },
        )
    }

    if (openContact) {
        RequestPermission(
            permission = PermissionState.READ_CONTACTS,
            openSetting = true,
            deniedDialogTitle = "App requires access to your contacts",
            deniedDialogDesc = "You can enable this permission in the settings",
            isGranted = { isGranted ->
                isGrantedContact = isGranted
                openContact = false
            },
        )
    }

    if (openNotification) {
        RequestPermission(
            permission = PermissionState.POST_NOTIFICATIONS,
            openSetting = true,
            deniedDialogTitle = "App requires access to send notifications",
            deniedDialogDesc = "You can enable this permission in the settings",
            isGranted = { isGranted ->
                isGrantedNotification = isGranted
                openNotification = false
            },
        )
    }

    if (openCamera) {
        RequestPermission(
            permission = PermissionState.CAMERA,
            openSetting = true,
            deniedDialogTitle = "App requires access to your camera",
            deniedDialogDesc = "You can enable this permission in the settings",
            isGranted = { isGranted ->
                isGrantedCamera = isGranted
                openCamera = false
            },
        )
    }

    if (openMicrophone) {
        RequestPermission(
            permission = PermissionState.RECORD_AUDIO,
            openSetting = true,
            deniedDialogTitle = "App requires access to your microphone",
            deniedDialogDesc = "You can enable this permission in the settings",
            isGranted = { isGranted ->
                isGrantedMicrophone = isGranted
                openMicrophone = false
            },
        )
    }

    if (openLocation) {
        RequestPermission(
            permission = PermissionState.ACCESS_FINE_LOCATION,
            openSetting = true,
            deniedDialogTitle = "App requires access to your location",
            deniedDialogDesc = "You can enable this permission in the settings",
            isGranted = { isGranted ->
                isGrantedLocation = isGranted
                openLocation = false
            },
        )
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Box(
                modifier = if (isGrantedStorage == null) {
                    Modifier
                        .background(Color(0xFFFFBB4E), shape = RoundedCornerShape(25.dp))
                        .weight(0.54f)
                        .noRippleClickable {
                            openStorage = true
                        }
                } else if (isGrantedStorage == true) {
                    Modifier
                        .background(Color(0xFF8AE98D), shape = RoundedCornerShape(25.dp))
                        .weight(0.54f)
                        .noRippleClickable {
                            openStorage = true
                        }
                } else {
                    Modifier
                        .background(Color(0xFFFF6C6C), shape = RoundedCornerShape(25.dp))
                        .weight(0.54f)
                        .noRippleClickable {
                            openStorage = true
                        }
                }
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 25.dp, vertical = 28.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    Image(
                        painter = painterResource(Res.drawable.ic_storage),
                        contentDescription = Res.drawable.ic_storage.toString(),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(38.dp)
                    )

                    Text(
                        text = "Storage\n" +
                                "Access",
                        color = ColorPalette.black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                    )
                }
            }

            Box(
                modifier = if (isGrantedContact == null) {
                    Modifier
                        .background(Color(0xFFFFBB4E), shape = RoundedCornerShape(25.dp))
                        .padding(horizontal = 25.dp, vertical = 28.dp)
                        .weight(0.38f)
                        .noRippleClickable {
                            openContact = true
                        }
                } else if (isGrantedContact == true) {
                    Modifier
                        .background(Color(0xFF8AE98D), shape = RoundedCornerShape(25.dp))
                        .padding(horizontal = 25.dp, vertical = 28.dp)
                        .weight(0.38f)
                        .noRippleClickable {
                            openContact = true
                        }
                } else {
                    Modifier
                        .background(Color(0xFFFF6C6C), shape = RoundedCornerShape(25.dp))
                        .padding(horizontal = 25.dp, vertical = 28.dp)
                        .weight(0.38f)
                        .noRippleClickable {
                            openContact = true
                        }
                }
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_user),
                    contentDescription = Res.drawable.ic_user.toString(),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(46.dp)
                        .align(Alignment.Center)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Box(
                modifier = if (isGrantedNotification == null) {
                    Modifier
                        .background(Color(0xFFFFBB4E), shape = RoundedCornerShape(25.dp))
                        .padding(horizontal = 25.dp, vertical = 28.dp)
                        .weight(0.38f)
                        .noRippleClickable {
                            openNotification = true
                        }
                } else if (isGrantedNotification == true) {
                    Modifier
                        .background(Color(0xFF8AE98D), shape = RoundedCornerShape(25.dp))
                        .padding(horizontal = 25.dp, vertical = 28.dp)
                        .weight(0.38f)
                        .noRippleClickable {
                            openNotification = true
                        }
                } else {
                    Modifier
                        .background(Color(0xFFFF6C6C), shape = RoundedCornerShape(25.dp))
                        .padding(horizontal = 25.dp, vertical = 28.dp)
                        .weight(0.38f)
                        .noRippleClickable {
                            openNotification = true
                        }
                }
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_notification),
                    contentDescription = Res.drawable.ic_notification.toString(),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(46.dp)
                        .align(Alignment.Center)
                )
            }

            Box(
                modifier = if (isGrantedCamera == null) {
                    Modifier
                        .background(Color(0xFFFFBB4E), shape = RoundedCornerShape(25.dp))
                        .weight(0.54f)
                        .noRippleClickable {
                            openCamera = true
                        }
                } else if (isGrantedCamera == true) {
                    Modifier
                        .background(Color(0xFF8AE98D), shape = RoundedCornerShape(25.dp))
                        .weight(0.54f)
                        .noRippleClickable {
                            openCamera = true
                        }
                } else {
                    Modifier
                        .background(Color(0xFFFF6C6C), shape = RoundedCornerShape(25.dp))
                        .weight(0.54f)
                        .noRippleClickable {
                            openCamera = true
                        }
                }
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 17.dp, vertical = 28.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Image(
                        painter = painterResource(Res.drawable.ic_camera),
                        contentDescription = Res.drawable.ic_camera.toString(),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .width(64.dp)
                            .height(34.dp)
                    )

                    Text(
                        text = "Camera \n" +
                                "Access",
                        color = ColorPalette.black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Box(
                modifier = if (isGrantedMicrophone == null) {
                    Modifier
                        .background(Color(0xFFFFBB4E), shape = RoundedCornerShape(25.dp))
                        .weight(0.54f)
                        .noRippleClickable {
                            openMicrophone = true
                        }
                } else if (isGrantedMicrophone == true) {
                    Modifier
                        .background(Color(0xFF8AE98D), shape = RoundedCornerShape(25.dp))
                        .weight(0.54f)
                        .noRippleClickable {
                            openMicrophone = true
                        }
                } else {
                    Modifier
                        .background(Color(0xFFFF6C6C), shape = RoundedCornerShape(25.dp))
                        .weight(0.54f)
                        .noRippleClickable {
                            openMicrophone = true
                        }
                }
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 25.dp, vertical = 28.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    Image(
                        painter = painterResource(Res.drawable.ic_microphone),
                        contentDescription = Res.drawable.ic_microphone.toString(),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(38.dp)
                    )

                    Text(
                        text = "Record\n" +
                                "Audio",
                        color = ColorPalette.black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                    )
                }
            }

            Box(
                modifier = if (isGrantedLocation == null) {
                    Modifier
                        .background(Color(0xFFFFBB4E), shape = RoundedCornerShape(25.dp))
                        .padding(horizontal = 25.dp, vertical = 28.dp)
                        .weight(0.38f)
                        .noRippleClickable {
                            openLocation = true
                        }
                } else if (isGrantedLocation == true) {
                    Modifier
                        .background(Color(0xFF8AE98D), shape = RoundedCornerShape(25.dp))
                        .padding(horizontal = 25.dp, vertical = 28.dp)
                        .weight(0.38f)
                        .noRippleClickable {
                            openLocation = true
                        }
                } else {
                    Modifier
                        .background(Color(0xFFFF6C6C), shape = RoundedCornerShape(25.dp))
                        .padding(horizontal = 25.dp, vertical = 28.dp)
                        .weight(0.38f)
                        .noRippleClickable {
                            openLocation = true
                        }
                }
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_location),
                    contentDescription = Res.drawable.ic_location.toString(),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(46.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun MorePermissionView() {
    var openActivityRecognition by remember { mutableStateOf(false) }
    var isGrantedActivityRecognition by remember { mutableStateOf<Boolean?>(null) }

    var openPhoneState by remember { mutableStateOf(false) }
    var isGrantedPhoneState by remember { mutableStateOf<Boolean?>(null) }

    var openVoiceMail by remember { mutableStateOf(false) }
    var isGrantedVoiceMail by remember { mutableStateOf<Boolean?>(null) }

    var openSMS by remember { mutableStateOf(false) }
    var isGrantedSMS by remember { mutableStateOf<Boolean?>(null) }

    var openCalendar by remember { mutableStateOf(false) }
    var isGrantedCalendar by remember { mutableStateOf<Boolean?>(null) }

    var openBluetooth by remember { mutableStateOf(false) }
    var isGrantedBluetooth by remember { mutableStateOf<Boolean?>(null) }

    if (openActivityRecognition) {
        RequestPermission(
            permission = PermissionState.ACTIVITY_RECOGNITION,
            openSetting = true,
            deniedDialogTitle = "App requires access to your activity recognition",
            deniedDialogDesc = "You can enable this permission in the settings",
            isGranted = { isGranted ->
                isGrantedActivityRecognition = isGranted
                openActivityRecognition = false
            },
        )
    }

    if (openPhoneState) {
        RequestPermission(
            permission = PermissionState.READ_PHONE_STATE,
            openSetting = true,
            deniedDialogTitle = "App requires access to your phone state",
            deniedDialogDesc = "You can enable this permission in the settings",
            isGranted = { isGranted ->
                isGrantedPhoneState = isGranted
                openPhoneState = false
            },
        )
    }

    if (openVoiceMail) {
        RequestPermission(
            permission = PermissionState.ADD_VOICEMAIL,
            openSetting = true,
            deniedDialogTitle = "App requires access to your voicemail",
            deniedDialogDesc = "You can enable this permission in the settings",
            isGranted = { isGranted ->
                isGrantedVoiceMail = isGranted
                openVoiceMail = false
            },
        )
    }

    if (openSMS) {
        RequestPermission(
            permission = PermissionState.SEND_SMS,
            openSetting = true,
            deniedDialogTitle = "App requires access to send SMS",
            deniedDialogDesc = "You can enable this permission in the settings",
            isGranted = { isGranted ->
                isGrantedSMS = isGranted
                openSMS = false
            },
        )
    }

    if (openCalendar) {
        RequestPermission(
            permission = PermissionState.READ_CALENDAR,
            openSetting = true,
            deniedDialogTitle = "App requires access to your calendar",
            deniedDialogDesc = "You can enable this permission in the settings",
            isGranted = { isGranted ->
                isGrantedCalendar = isGranted
                openCalendar = false
            },
        )
    }

    if (openBluetooth) {
        RequestPermission(
            permission = PermissionState.BLUETOOTH,
            openSetting = true,
            deniedDialogTitle = "App requires access to your Bluetooth",
            deniedDialogDesc = "You can enable this permission in the settings",
            isGranted = { isGranted ->
                isGrantedBluetooth = isGranted
                openBluetooth = false
            },
        )
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "More permissions",
                color = ColorPalette.textSecondary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
                modifier = Modifier
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        ListItem(
            "Activity Recognition Permission",
            "Activity Recognition information",
            isGrantedActivityRecognition
        ) {
            openActivityRecognition = true
        }

        Spacer(modifier = Modifier.height(20.dp))

        ListItem("Phone State Permission", "Phone State information access", isGrantedPhoneState) {
            openPhoneState = true
        }

        Spacer(modifier = Modifier.height(20.dp))

        ListItem("Voicemail Permission", "Add Voicemail information access", isGrantedVoiceMail) {
            openVoiceMail = true
        }

        Spacer(modifier = Modifier.height(20.dp))

        ListItem("SMS Permission", "SMS information access", isGrantedSMS) {
            openSMS = true
        }

        Spacer(modifier = Modifier.height(20.dp))

        ListItem("Calendar Permission", "Calendar information access", isGrantedCalendar) {
            openCalendar = true
        }

        Spacer(modifier = Modifier.height(20.dp))

        ListItem("Bluetooth Permission", "Bluetooth information access", isGrantedBluetooth) {
            openBluetooth = true
        }
    }
}

@Composable
fun ListItem(title: String, desc: String, state: Boolean?, onClick: () -> Unit) {
    Column(
        modifier = Modifier.noRippleClickable {
            onClick()
        },
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = title,
                    color = ColorPalette.white,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = desc,
                    color = ColorPalette.textOther,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                )
            }

            if (state != null) {
                Spacer(modifier = Modifier.weight(1f))
                if (state)
                    Image(
                        painter = painterResource(Res.drawable.ic_true),
                        contentDescription = Res.drawable.ic_true.toString(),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(36.dp)
                    )
                else
                    Image(
                        painter = painterResource(Res.drawable.ic_false),
                        contentDescription = Res.drawable.ic_false.toString(),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(36.dp)
                    )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        HorizontalDivider(
            modifier = Modifier, thickness = 1.dp,
            color = ColorPalette.divider
        )
    }
}

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

internal expect fun openUrl(url: String?)