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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.spidex.safe.ui.theme.detail_size_sp
import com.spidex.safe.ui.theme.extra_padding
import com.spidex.safe.ui.theme.green
import com.spidex.safe.ui.theme.header_size_sp
import com.spidex.safe.ui.theme.normal_elevation
import com.spidex.safe.ui.theme.normal_padding
import com.spidex.safe.ui.theme.red
import com.spidex.safe.ui.theme.roundedCornerShape
import com.spidex.safe.ui.theme.subhead_size_sp
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
            .padding(top = extra_padding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = normal_padding),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFe5e7e8)),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 0.dp
                )
            ) {
                Text(
                    text = stringResource(R.string.security_header),
                    fontSize = header_size_sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(normal_padding))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(normal_padding),
            colors = CardDefaults.cardColors(containerColor = red),
            onClick = {},
            elevation = CardDefaults.cardElevation(
                defaultElevation = normal_elevation
            ),
            shape = RoundedCornerShape(roundedCornerShape)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(normal_padding)
            ) {
                val (text1, text2, image) = createRefs()
                Text(
                    text = stringResource(R.string.sos_header),
                    fontWeight = FontWeight.Bold,
                    fontSize = subhead_size_sp,
                    color = Color.White,
                    modifier = Modifier.constrainAs(text1) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                )

                Text(
                    text = stringResource(R.string.sos_detail),
                    fontSize = detail_size_sp,
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
                            top.linkTo(parent.top, margin = normal_padding)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom, margin = normal_padding)
                        }
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(normal_padding),
            colors = CardDefaults.cardColors(containerColor = green),
            onClick = {},
            elevation = CardDefaults.cardElevation(
                defaultElevation = normal_elevation
            ),
            shape = RoundedCornerShape(roundedCornerShape)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(normal_padding)
            ) {
                val (text1, text2, image) = createRefs()
                Text(
                    text = stringResource(R.string.guard_header),
                    fontWeight = FontWeight.Bold,
                    fontSize = subhead_size_sp,
                    color = Color.White,
                    modifier = Modifier.constrainAs(text1) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                )

                Text(
                    text = stringResource(R.string.guard_detail),
                    fontSize = detail_size_sp,
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
                            top.linkTo(parent.top, margin = normal_padding)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom, margin = normal_padding)
                        }
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(normal_padding),
            colors = CardDefaults.cardColors(containerColor = yellow),
            onClick = {},
            elevation = CardDefaults.cardElevation(
                defaultElevation = normal_elevation
            ),
            shape = RoundedCornerShape(roundedCornerShape)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(normal_padding)
            ) {
                val (text1, text2, image) = createRefs()
                Text(
                    text = stringResource(R.string.outside_house_header),
                    fontWeight = FontWeight.Bold,
                    fontSize = subhead_size_sp,
                    color = Color.White,
                    modifier = Modifier.constrainAs(text1) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                )

                Text(
                    text = stringResource(R.string.outside_detail),
                    fontSize = detail_size_sp,
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
                            top.linkTo(parent.top, margin = normal_padding)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom, margin = normal_padding)
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