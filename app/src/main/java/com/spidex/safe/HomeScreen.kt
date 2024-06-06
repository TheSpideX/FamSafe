@file:Suppress("DEPRECATION")

package com.spidex.safe

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.spidex.safe.ui.theme.green
import com.spidex.safe.ui.theme.header_size_sp
import com.spidex.safe.ui.theme.home_icon_dimension
import com.spidex.safe.ui.theme.icon_dimension
import com.spidex.safe.ui.theme.lesser_elevation
import com.spidex.safe.ui.theme.lightGreen
import com.spidex.safe.ui.theme.lightRed
import com.spidex.safe.ui.theme.lightYellow
import com.spidex.safe.ui.theme.normal_padding
import com.spidex.safe.ui.theme.red
import com.spidex.safe.ui.theme.roundedCornerShape

@Composable
fun HomeScreen(tempListData : List<PersonData>,navigateToMaps : () -> Unit){

    var listData by remember { mutableStateOf(tempListData) }
    val expandedCardIndex = remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFe5e7e8))
            .padding(normal_padding)
            .padding(top = normal_padding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = normal_padding),
            horizontalArrangement = Arrangement.SpaceBetween
        ){

            IconButton(onClick = {}) {
                Image(
                    painter = painterResource(id = R.drawable.ic_location_false),
                    contentDescription = null,
                    modifier = Modifier
                        .width(icon_dimension)
                        .height(icon_dimension)
                )
            }
            
            Text(
                text = "My Family",
                fontWeight = FontWeight.Bold,
                fontSize = header_size_sp,
                color = Color.Black
            )

            IconButton(onClick = {}) {
                Image(
                    painter = painterResource(id = R.drawable.ic_more_option_false),
                    contentDescription = null,
                    modifier = Modifier
                        .width(icon_dimension)
                        .height(icon_dimension)
                )
            }
        }

        LazyColumn (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = normal_padding)
        ){
            itemsIndexed(tempListData) { index, item ->
                ShowData(
                    item = item,
                    navigateToMaps = navigateToMaps,
                    isExpanded = expandedCardIndex.value == index, // Determine if this card is expanded
                    onCardClick = { newIndex ->
                        expandedCardIndex.value = if (expandedCardIndex.value == newIndex) null else newIndex
                        // Toggle expansion or collapse if already expanded
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ShowData(item: PersonData, navigateToMaps: () -> Unit,isExpanded: Boolean, onCardClick: (Int) -> Unit) {

    val extraHeight by animateDpAsState(if (isExpanded) 40.dp else 0.dp)
    var isPlaying by remember { mutableStateOf(true) }
    val squareSize = 80.dp
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.button_home))
    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = isPlaying,
    )


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp + extraHeight)
            .padding(top = normal_padding)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp + extraHeight)
        ) {
            val (mainCard, detail) = createRefs()

            Image(
                painter = painterResource(
                    id = when {
                        item.cond == 0 -> R.drawable.ic_home_icon_sos
                        item.cond == 1 -> R.drawable.ic_home_icon_guard
                        else -> R.drawable.ic_home_icon_outside
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .width(32.dp)
                    .height(32.dp)
                    .zIndex(1f)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(140.dp)
                    .constrainAs(mainCard) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                shape = RoundedCornerShape(roundedCornerShape),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = lesser_elevation),
                onClick = {onCardClick(item.id)}
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .padding(8.dp)
                ) {
                    val (image, name, location, battery, userDeviceDetail, time, button) = createRefs()

                    LottieAnimation(
                        composition = composition,
                        modifier = Modifier
                            .constrainAs(button) {
                                bottom.linkTo(parent.bottom)
                                end.linkTo(parent.end)
                            }
                            .width(24.dp).height(24.dp),
                        progress = progress
                    )


                    Text(
                        text = item.time,
                        color = Color(0x9A808080),
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        modifier = Modifier
                            .constrainAs(time) {
                                top.linkTo(parent.top, margin = 4.dp)
                                end.linkTo(parent.end, margin = 8.dp)
                            }
                    )

                    Image(
                        painter = painterResource(id = item.icon),
                        contentDescription = null,
                        modifier = Modifier
                            .width(home_icon_dimension)
                            .height(home_icon_dimension)
                            .padding(2.dp)
                            .constrainAs(image) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start, margin = 8.dp)
                                bottom.linkTo(battery.top)
                            }
                    )

                    Row(
                        modifier = Modifier
                            .wrapContentHeight()
                            .width(home_icon_dimension)
                            .clip(shape = RoundedCornerShape(24))
                            .constrainAs(battery) {
                                top.linkTo(image.bottom)
                                start.linkTo(parent.start, margin = 8.dp)
                                bottom.linkTo(parent.bottom)
                            }
                            .background(
                                color = when {
                                    item.battery <= 30 -> lightRed
                                    item.battery in 31..60 -> lightYellow
                                    else -> lightGreen
                                }
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Image(
                            painter = painterResource(
                                id = when {
                                    item.battery <= 30 -> R.drawable.ic_low_battery
                                    item.battery in 31..60 -> R.drawable.ic_half_battery
                                    else -> R.drawable.ic_full_battery
                                }
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .width(18.dp)
                                .height(18.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = " ${item.battery}%",
                            color = when {
                                item.battery <= 30 -> red
                                item.battery in 31..60 -> Color(0xFFe8c47d)
                                else -> green
                            },
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }

                    Text(
                        text = item.name,
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .offset(0.dp, (-4).dp)
                            .constrainAs(name) {
                                top.linkTo(parent.top)
                                start.linkTo(image.end, margin = normal_padding)
                                bottom.linkTo(location.top)
                                width = Dimension.fillToConstraints
                            }
                            .horizontalScroll(rememberScrollState()),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Row(
                        modifier = Modifier
                            .offset(0.dp, (0).dp)
                            .constrainAs(location) {
                                top.linkTo(name.bottom)
                                start.linkTo(image.end, margin = normal_padding)
                                end.linkTo(button.start)
                                bottom.linkTo(userDeviceDetail.top)
                                width = Dimension.fillToConstraints
                            }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_location),
                            contentDescription = null,
                            modifier = Modifier
                                .width(16.dp)
                                .height(16.dp)
                        )

                        Text(
                            text = item.location,
                            color = Color(0x9A808080),
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            letterSpacing = (0.6).sp,
                            modifier = Modifier
                                .weight(1f),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Row(
                        modifier = Modifier
                            .offset(0.dp, 4.dp)
                            .constrainAs(userDeviceDetail) {
                                top.linkTo(location.bottom)
                                start.linkTo(image.end, margin = normal_padding)
                                bottom.linkTo(parent.bottom)
                                width = Dimension.preferredWrapContent
                                height = Dimension.wrapContent
                            },
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Row(
                            modifier = Modifier.wrapContentSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_location_distance),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(18.dp)
                                    .height(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${item.distance}m",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                        Spacer(modifier = Modifier.width(42.dp))

                        Row(
                            modifier = Modifier.wrapContentSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(
                                    id = when {
                                        item.network == "WiFi" -> R.drawable.ic_wifi
                                        item.network == "4G" || item.network == "5G" -> R.drawable.ic_signal
                                        else -> R.drawable.ic_no_connection
                                    }
                                ),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(18.dp)
                                    .height(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = item.network,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
            AnimatedVisibility(
                visible = isExpanded,
                modifier = Modifier.constrainAs(detail) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                Text(
                    text = "Details"
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true,
    device = "id:pixel_7_pro"
)
@Composable
fun HomeScreenPreview(){
    val newItem0 = PersonData(
        name = "Satyam",
        location = "Chankya Nagar, Kumhrar,Patna,Bihar,800026,India",
        battery = 90,
        distance = 233,
        network = "4G",
        mobile = "9142299756",
        icon = R.drawable.ic_man,
        cond = 2,
        time = "12:00",
        id = 1
    )

    val tempListData : List<PersonData> = listOf(newItem0)
    HomeScreen(tempListData,{})
}