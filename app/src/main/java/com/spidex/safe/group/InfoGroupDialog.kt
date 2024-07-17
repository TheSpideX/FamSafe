package com.spidex.safe.group

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.spidex.safe.R

@Composable
fun InfoGroupDialog(
    onSelectClick : () -> Unit,
    onLeaveClick : (String) -> Unit,
    onDeleteClick : () -> Unit,
    onCancelClick : () -> Unit
){
    val context = LocalContext.current
    val clipBoard = context.getSystemService(
        Context.CLIPBOARD_SERVICE
    ) as ClipboardManager
    Dialog(onDismissRequest = {onCancelClick()}) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(
                        topStart = 2.dp,
                        topEnd = 16.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 2.dp
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_group_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Name",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier
                        .width(80.dp)
                        .horizontalScroll(rememberScrollState())
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Divider(
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Member : 8",
                modifier = Modifier.fillMaxWidth(0.85f),
                textAlign = TextAlign.Center,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Code : ADSAKJ1J",
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .clickable {
                        val clip = ClipData.newPlainText("copied", "ADSAKJ1J")
                        clipBoard.setPrimaryClip(clip)
                        Toast
                            .makeText(context, "Group Code Copied", Toast.LENGTH_LONG)
                            .show()
                    },
                textAlign = TextAlign.Center,
                color = Color.Black,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Note : If you are the admin, then the Group will be deleted",
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .clickable {
                        val clip = ClipData.newPlainText("copied", "ADSAKJ1J")
                        clipBoard.setPrimaryClip(clip)
                        Toast
                            .makeText(context, "Group Code Copied", Toast.LENGTH_LONG)
                            .show()
                    },
                textAlign = TextAlign.Center,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Divider(
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Spacer(modifier = Modifier.height(2.dp))
            Row(
                modifier = Modifier.fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {  },
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color(0xFF2FD163),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth(0.45f),
                    shape = RoundedCornerShape(20)
                ) {
                    Text(text = "Select")
                }
                Button(
                    onClick = { },
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color(0xFFFF504A),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth(0.85f),
                    shape = RoundedCornerShape(20)
                ) {
                    Text(text = "Leave")
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            Button(
                onClick = { onCancelClick() },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Gray
                ),
                modifier = Modifier.fillMaxWidth(0.85f),
                shape = RoundedCornerShape(20)
            ) {
                Text(text = "Cancel")
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InfoGroupDialogPreview(){
    InfoGroupDialog({},{},{}){}
}