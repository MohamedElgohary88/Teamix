package com.chocolate.viewmodel.createChannel

import androidx.lifecycle.SavedStateHandle

class CreateChannelArgs(savedStateHandle: SavedStateHandle) {
    val channelName: String = checkNotNull(savedStateHandle[CHANNEL_NAME])

    companion object {
        const val CHANNEL_NAME = "channelName"
    }
}