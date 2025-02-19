package network.chaintech.cmpeasypermission.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import network.chaintech.sdpcomposemultiplatform.sdp

@Composable
fun MyAlertDialog(
    dialogParams: DialogParams,
    onConfirmButtonClicked: (() -> Unit)? = null,
    onDismissButtonClicked: (() -> Unit)? = null,
) {

    val title = dialogParams.titleStr
    val message = dialogParams.messageStr
    val isCancelable = dialogParams.isCancelable
    val titleTextStyle = dialogParams.titleTextStyle

    val confirmButton: @Composable () -> Unit = {
        Box(
            modifier = Modifier
                .padding(top = 10.sdp)
                .background(Color(0xFF4579FF), RoundedCornerShape(50.sdp))
                .width(100.sdp).height(36.sdp),
            contentAlignment = Alignment.Center
        ) {
            TextButton(onClick = {
                onConfirmButtonClicked?.invoke()
            }, modifier = Modifier.fillMaxWidth()) {
                DialogButtonText("Ok", dialogParams.positiveButtonTextStyle)
            }
        }
    }

    val dismissButton: @Composable () -> Unit = {
        Box(
            modifier = Modifier
                .padding(top = 10.sdp, end = 12.sdp)
                .border(1.sdp, Color.Black, RoundedCornerShape(50.sdp))
                .width(100.sdp).height(36.sdp),
            contentAlignment = Alignment.Center
        ) {
            TextButton(onClick = {
                onDismissButtonClicked?.invoke()
            }, modifier = Modifier.fillMaxWidth()) {
                DialogButtonText("Cancel", dialogParams.negativeButtonTextStyle)
            }
        }
    }

    val dialogTitle: (@Composable () -> Unit)? = title?.let {
        @Composable {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.sdp)
            ) {
                Text(
                    text = it,
                    style = TextStyle(
                        color = titleTextStyle.color,
                        fontWeight = titleTextStyle.fontWeight ?: FontWeight.Bold,
                        fontSize = titleTextStyle.fontSize,
                        fontFamily = titleTextStyle.fontFamily,
                        fontStyle = titleTextStyle.fontStyle,
                        textAlign = titleTextStyle.textAlign
                    ),
                )
            }
        }
    }


    AlertDialog(
        onDismissRequest = { onDismissButtonClicked?.invoke() },
        confirmButton = { confirmButton.invoke() },
        dismissButton = { dismissButton.invoke() },
        title = { dialogTitle?.invoke() },
        text = { DialogMessage(value = message, style = dialogParams.messageTextStyle) },
        properties = DialogProperties(
            dismissOnBackPress = isCancelable,
            dismissOnClickOutside = isCancelable,
            usePlatformDefaultWidth = false
        ),
        shape = RoundedCornerShape(14.sdp),
        modifier = Modifier.padding(12.sdp),
        containerColor = Color.White
    )
}

@Composable
private fun DialogMessage(value: String, style: TextStyleParams) {
    Text(
        text = value,
        style = TextStyle(
            color = style.color,
            fontWeight = style.fontWeight,
            fontSize = style.fontSize,
            fontFamily = style.fontFamily,
            fontStyle = style.fontStyle,
            textAlign = style.textAlign
        ),
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun DialogButtonText(value: String, style: TextStyleParams) {
    Text(
        text = value,
        style = TextStyle(
            color = style.color,
            fontWeight = style.fontWeight,
            fontSize = style.fontSize,
            fontFamily = style.fontFamily,
            fontStyle = style.fontStyle,
            textAlign = style.textAlign
        ),
    )
}

data class DialogParams(
    val titleStr: String? = null,
    val messageStr: String = "",
    val titleTextStyle: TextStyleParams = TextStyleParams(
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    ),
    val messageTextStyle: TextStyleParams = TextStyleParams(
        fontSize = 16.sp,
        color = Color.Gray
    ),
    val positiveButtonTextStyle: TextStyleParams = TextStyleParams(
        fontSize = 16.sp,
        color = Color.White
    ),
    val negativeButtonTextStyle: TextStyleParams = TextStyleParams(
        fontSize = 16.sp,
        color = Color.Black
    ),
    val isCancelable: Boolean = false
)

data class TextStyleParams(
    val color: Color = Color.Unspecified,
    val fontSize: TextUnit = TextUnit.Unspecified,
    val fontStyle: FontStyle? = null,
    val fontWeight: FontWeight? = null,
    val fontFamily: FontFamily? = null,
    val textAlign: TextAlign = TextAlign.Start,
)

