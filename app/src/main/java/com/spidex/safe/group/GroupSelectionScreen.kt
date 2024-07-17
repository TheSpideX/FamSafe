package com.spidex.safe.group

import android.app.Application
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.exyte.animatednavbar.utils.noRippleClickable
import com.spidex.safe.R
import com.spidex.safe.data.GroupData

@Composable
fun GroupSelectionScreen(
    viewModel: GroupViewModel,
    onBackClick : () -> Unit,
    onGroupSelected: (GroupData) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val showJoinDialog by viewModel.showJoinGroupDialog.collectAsState()
    val showCreateDialog by viewModel.showCreateGroupDialog.collectAsState()
    val cutoutPadding = WindowInsets.displayCutout.asPaddingValues()

    BackHandler {
        onBackClick()
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Box(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.BottomEnd)
                .padding(16.dp),
        ) {
            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn(animationSpec = tween(300)),
                exit = fadeOut(animationSpec = tween(300)),
                content = {
                    Box(
                        modifier = Modifier.size(120.dp)
                    ) {
                        FloatingActionButton(
                            onClick = { viewModel.setCreateGroupDialog(true) },
                            containerColor = Color.White,
                            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp),
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_create_group),
                                contentDescription = "Add",
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(color = Color.White, shape = RoundedCornerShape(20))
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        FloatingActionButton(
                            onClick = { viewModel.setJoinGroupDialog(true) },
                            containerColor = Color.White,
                            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp),
                            modifier = Modifier.align(Alignment.BottomStart)
                        )
                        {
                            Image(
                                painter = painterResource(id = R.drawable.ic_join_group),
                                contentDescription = "Join",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            FloatingActionButton(
                onClick = { expanded = !expanded },
                modifier = Modifier
                    .scale(if (expanded) 0.75f else 1f)
                    .align(Alignment.BottomEnd),
                containerColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp)
            ) {
                Icon(
                    imageVector = if(!expanded)Icons.Default.Settings else Icons.Default.Clear,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(cutoutPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ){
                Text(
                    text = "Group",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
                    Image(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .size(48.dp)
                            .padding(start = 16.dp)
                            .noRippleClickable {
                                onBackClick()
                            }
                    )
            }

            Spacer(modifier = Modifier.height(8.dp))
            GroupView(){

            }
        }


        if(showJoinDialog){
            JoinGroupDialog(onJoinClick = {}, onCancelClick = {
                viewModel.setJoinGroupDialog(false)
            })
        }

        if(showCreateDialog){
            CreateGroupDialog(onCreateClick = {name ->
                viewModel.createGroup(name)
            }, onCancelClick = {
                viewModel.setCreateGroupDialog(false)
            })
        }
    }
}

@Composable
fun GroupView(onGroupClick : () -> Unit){
    var offsetX by remember { mutableStateOf(0f) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
            .background(color = Color.Black, shape = RoundedCornerShape(20))
    ){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(start = 32.dp)
                .align(Alignment.Center),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(20),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 20.dp),
            onClick = {
                onGroupClick()
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Group Name",
                    fontSize = 20.sp
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GroupSelectionScreenPreview(){
    val groupViewModel: GroupViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory(LocalContext.current.applicationContext as Application)
    )
    GroupSelectionScreen(viewModel = groupViewModel,{}) {
        
    }
}

