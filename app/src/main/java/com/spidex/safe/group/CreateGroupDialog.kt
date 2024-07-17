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
fun CreateGroupDialog(
    onCreateClick : (String) -> Unit,
    onCancelClick : () -> Unit
){
    var name by remember {mutableStateOf("")}
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
                    painter = painterResource(id = R.drawable.ic_create_group),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Create Group",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Create a group and share the code for other member to join",
                modifier = Modifier.fillMaxWidth(0.85f),
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = {name = it},
                modifier = Modifier.fillMaxWidth(0.85f),
                singleLine = true,
                label = {
                    Text(text = "Name")
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color(0xFF38004D),
                    unfocusedLabelColor = Color.Gray,
                    focusedBorderColor = Color(0xFF38004D),
                    unfocusedBorderColor = Color.Gray,
                    focusedTextColor = Color(0xFF38004D),
                    unfocusedTextColor = Color.Gray,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onCreateClick(name) },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color(0xFF38004D),
                    contentColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth(0.85f),
                shape = RoundedCornerShape(20)
            ) {
                Text(text = "Create Group")
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
fun CreateGroupDialogPreview(){
    CreateGroupDialog({}){}
}