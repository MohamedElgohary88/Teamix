package com.chocolate.presentation.screens.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.chocolate.presentation.R
import com.chocolate.presentation.screens.profile.component.ChannelNameSheet
import com.chocolate.presentation.screens.profile.component.MultiChoiceDialog
import com.chocolate.presentation.screens.profile.component.OrganizationImageSheet
import com.chocolate.presentation.screens.profile.component.OrganizationNameSheet
import com.chocolate.presentation.screens.profile.component.SettingCard
import com.chocolate.presentation.theme.Space12
import com.chocolate.presentation.theme.Space16
import com.chocolate.presentation.theme.Space8
import com.chocolate.presentation.theme.Space80
import com.chocolate.presentation.theme.TeamixTheme
import com.chocolate.presentation.theme.Thickness2
import com.chocolate.presentation.theme.customColors

@Composable
fun OwnerPowerScreen(navController: NavController) {
    OwnerPowerContent()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerPowerContent() {
    val color = MaterialTheme.customColors()
    var showDialog by remember { mutableStateOf(false) }
    var organizationImageSheet by remember { mutableStateOf(false) }
    var organizationNameSheet by remember { mutableStateOf(false) }
    var channelNameSheet by remember { mutableStateOf(false) }


    if (showDialog) {
        MultiChoiceDialog(
            onClick = { showDialog = false },
            list = listOf(stringResource(R.string.guest),
                stringResource(R.string.member), stringResource(R.string.administrator),
                stringResource(
                    R.string.owner
                )
            )
        )
    }
    if (organizationNameSheet) {
        OrganizationNameSheet(onClick = { organizationNameSheet = false }, color = color)
    }
    if (channelNameSheet) {
        ChannelNameSheet(onClick = { channelNameSheet = false }, color = color)
    }
    if (organizationImageSheet) {
        OrganizationImageSheet(onClick = { organizationImageSheet = false }, color = color)
    }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = stringResource(id = R.string.owner_powers),
                    style = MaterialTheme.typography.titleMedium,
                    color = color.onBackground87
                )
            }, navigationIcon = {
                Image(
                    painter = painterResource(id = R.drawable.alt_arrow_left),
                    contentDescription = null
                )
            }, colors = TopAppBarDefaults.topAppBarColors(color.onPrimary))
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Space16)
        ) {
            item {
                Text(
                    text = stringResource(R.string.organization), style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = Space80)
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = Space8)
                        .clip(RoundedCornerShape(Space12))
                        .wrapContentHeight()
                        .background(color.card)
                ) {
                    Column {
                        SettingCard(
                            click = { //navigate to members screen
                                    },
                            text = stringResource(R.string.invites_user),
                            icon = painterResource(id = R.drawable.invitesuser)
                        )
                        Divider(color = color.background, thickness = Thickness2)
                        SettingCard(
                            click = { organizationNameSheet = true },
                            text = stringResource(R.string.edit_organization_name),
                            icon = painterResource(id = R.drawable.editorganizationname)
                        )
                        Divider(color = color.background, thickness = Thickness2)
                        SettingCard(
                            click = { organizationImageSheet = true },
                            text = stringResource(R.string.edit_organization_image),
                            icon = painterResource(id = R.drawable.organizationimage)
                        )
                    }
                }
            }
            item {
                Text(
                    text = stringResource(R.string.permissions_roles), style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = Space16)
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = Space8)
                        .clip(RoundedCornerShape(Space12))
                        .wrapContentHeight()
                        .background(color.card)
                ) {
                    Column {
                        SettingCard(
                            click = { showDialog = true },
                            text = stringResource(R.string.change_member_role),
                            icon = painterResource(id = R.drawable.ownerpowers)
                        )
                        Divider(color = color.background, thickness = Thickness2)
                        SettingCard(
                            click = { //navigate to members screen
                                    },
                            text = stringResource(R.string.who_invites_users),
                            icon = painterResource(id = R.drawable.whoinviteusers)
                        )
                        Divider(color = color.background, thickness = Thickness2)
                        SettingCard(
                            click = { //navigate to members screen
                            },
                            text = stringResource(R.string.who_can_add_custom_emoji),
                            icon = painterResource(id = R.drawable.userheart)
                        )
                        Divider(color = color.background, thickness = Thickness2)
                        SettingCard(
                            click = {//navigate to members screen
                                 },
                            text = stringResource(R.string.who_can_use_all_everyone_mentions),
                            icon = painterResource(id = R.drawable.mentions)
                        )
                    }
                }
            }
            item {
                Text(
                    text = stringResource(R.string.channel), style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = Space16)
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = Space8)
                        .clip(RoundedCornerShape(Space12))
                        .wrapContentHeight()
                        .background(color.card)
                ) {
                    Column {
                        SettingCard(
                            click = { channelNameSheet = true },
                            text = stringResource(R.string.create_channel),
                            icon = painterResource(id = R.drawable.channel)
                        )
                        Divider(color = color.background, thickness = Thickness2)
                        SettingCard(
                            click = {
                                //navigate to channels  screen
                            },
                            text = stringResource(R.string.delete_channel),
                            icon = painterResource(id = R.drawable.deletechannel),
                            textColor = color.red
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OwnerPowerScreenPreview() {
    OwnerPowerScreen(rememberNavController())
}



