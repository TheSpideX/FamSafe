package com.spidex.safe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.spidex.safe.ui.theme.green
import com.spidex.safe.ui.theme.red
import com.spidex.safe.ui.theme.yellow
import androidx.compose.material3.Card as Card

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = Color(0xFFe5e7e8))
            .statusBarsPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFe5e7e8)),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 0.dp
                )
            ) {
                Text(
                    text = stringResource(R.string.security_header),
                    fontSize = 32.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = red),
            onClick = {},
            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            ),
            shape = RoundedCornerShape(16)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                val (text1, text2, image) = createRefs()
                Text(
                    text = stringResource(R.string.sos_header),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.White,
                    modifier = Modifier.constrainAs(text1) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                )

                Text(
                    text = stringResource(R.string.sos_detail),
                    fontSize = 16.sp,
                    color = Color(0xB2FFFFFF),
                    modifier = Modifier.constrainAs(text2) {
                        top.linkTo(text1.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(image.start, margin = 8.dp)
                        width = Dimension.fillToConstraints
                    }
                )


                Image(
                    painter = painterResource(id = R.drawable.ic_sos1),
                    contentDescription = "",
                    modifier = Modifier
                        .aspectRatio(0.9f)
                        .constrainAs(image) {
                            top.linkTo(parent.top, margin = 16.dp)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom, margin = 16.dp)
                        }
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = green),
            onClick = {},
            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            ),
            shape = RoundedCornerShape(16)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                val (text1, text2, image) = createRefs()
                Text(
                    text = stringResource(R.string.guard_header),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.White,
                    modifier = Modifier.constrainAs(text1) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                )

                Text(
                    text = stringResource(R.string.guard_detail),
                    fontSize = 16.sp,
                    color = Color(0xB2FFFFFF),
                    modifier = Modifier.constrainAs(text2) {
                        top.linkTo(text1.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(image.start, margin = 8.dp)
                        width = Dimension.fillToConstraints
                    }
                )


                Image(
                    painter = painterResource(id = R.drawable.ic_guard),
                    contentDescription = "",
                    modifier = Modifier
                        .aspectRatio(0.9f)
                        .constrainAs(image) {
                            top.linkTo(parent.top, margin = 16.dp)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom, margin = 16.dp)
                        }
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = yellow),
            onClick = {},
            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            ),
            shape = RoundedCornerShape(16)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                val (text1, text2, image) = createRefs()
                Text(
                    text = stringResource(R.string.outside_house_header),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.White,
                    modifier = Modifier.constrainAs(text1) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                )

                Text(
                    text = stringResource(R.string.outside_detail),
                    fontSize = 16.sp,
                    color = Color(0xB2FFFFFF),
                    modifier = Modifier.constrainAs(text2) {
                        top.linkTo(text1.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(image.start, margin = 8.dp)
                        width = Dimension.fillToConstraints
                    }
                )


                Image(
                    painter = painterResource(id = R.drawable.ic_outside),
                    contentDescription = "",
                    modifier = Modifier
                        .aspectRatio(0.9f)
                        .constrainAs(image) {
                            top.linkTo(parent.top, margin = 16.dp)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom, margin = 16.dp)
                        }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SecurityPreview(){
    SecurityScreen()
}