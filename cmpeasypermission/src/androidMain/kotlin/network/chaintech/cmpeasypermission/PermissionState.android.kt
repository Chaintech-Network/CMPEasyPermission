package network.chaintech.cmpeasypermission

import android.Manifest

actual enum class PermissionState(val value: String) {
    STORAGE(""),  // Now dynamically mapped based on API level
    CAMERA(Manifest.permission.CAMERA),
    RECORD_AUDIO(Manifest.permission.RECORD_AUDIO),
    ACCESS_FINE_LOCATION(Manifest.permission.ACCESS_FINE_LOCATION),
    ACCESS_COARSE_LOCATION(Manifest.permission.ACCESS_COARSE_LOCATION),
    POST_NOTIFICATIONS(Manifest.permission.POST_NOTIFICATIONS),
    ACTIVITY_RECOGNITION(Manifest.permission.ACTIVITY_RECOGNITION),
    READ_PHONE_STATE(Manifest.permission.READ_PHONE_STATE),
    CALL_PHONE(Manifest.permission.CALL_PHONE),
    READ_CALL_LOG(Manifest.permission.READ_CALL_LOG),
    WRITE_CALL_LOG(Manifest.permission.WRITE_CALL_LOG),
    ADD_VOICEMAIL(Manifest.permission.ADD_VOICEMAIL),
    ANSWER_PHONE_CALLS(Manifest.permission.ANSWER_PHONE_CALLS),
    USE_SIP(Manifest.permission.USE_SIP),
    READ_PHONE_NUMBERS(Manifest.permission.READ_PHONE_NUMBERS),
    SEND_SMS(Manifest.permission.SEND_SMS),
    RECEIVE_SMS(Manifest.permission.RECEIVE_SMS),
    RECEIVE_WAP_PUSH(Manifest.permission.RECEIVE_WAP_PUSH),
    READ_SMS(Manifest.permission.READ_SMS),
    RECEIVE_MMS(Manifest.permission.RECEIVE_MMS),
    READ_CALENDAR(Manifest.permission.READ_CALENDAR),
    WRITE_CALENDAR(Manifest.permission.WRITE_CALENDAR),
    READ_CONTACTS(Manifest.permission.READ_CONTACTS),
    WRITE_CONTACTS(Manifest.permission.WRITE_CONTACTS),
    GET_ACCOUNTS(Manifest.permission.GET_ACCOUNTS),
    ACCESS_MEDIA_LOCATION(Manifest.permission.ACCESS_MEDIA_LOCATION),
    BLUETOOTH(Manifest.permission.BLUETOOTH),
}