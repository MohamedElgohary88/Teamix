package com.chocolate.viewmodel.createchannel

interface CreateChannelInteraction {
    fun onNextClicked()
    fun onChannelNameTextChange(channelName: String)
    fun onChannelDescriptionChange(channelDescription: String?)
    fun onChannelStatusChange(newChannelStatus: ChannelStatus, isPrivate: Boolean)
}