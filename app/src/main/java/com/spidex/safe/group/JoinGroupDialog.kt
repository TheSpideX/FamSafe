package com.spidex.safe.group

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.spidex.safe.R

@Composable
fun JoinGroupDialog(
    onJoinClick : (String) -> Unit,
    onCancelClick : () -> Unit
){
    var code by remember {mutableStateOf("")}
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
                    painter = painterResource(id = R.drawable.ic_join_group),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Join Group",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Join a group so you can make sure your safety",
                modifier = Modifier.fillMaxWidth(0.85f),
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = code,
                onValueChange = {code = it},
                modifier = Modifier.fillMaxWidth(0.85f),
                singleLine = true,
                label = {
                    Text(text = "Group Code")
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color(0xFFB41D57),
                    unfocusedLabelColor = Color.Gray,
                    focusedBorderColor = Color(0xFFB41D57),
                    unfocusedBorderColor = Color.Gray,
                    focusedTextColor = Color(0xFFB41D57),
                    unfocusedTextColor = Color.Gray,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onJoinClick(code) },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color(0xFFB41D57),
                    contentColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth(0.85f),
                shape = RoundedCornerShape(20)
            ) {
                Text(text = "Join Group")
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
fun JoinGroupDialogPreview(){
    JoinGroupDialog({}){}
}