package com.chocolate.viewmodel.search

import com.chocolate.entities.channel.Channel

@JvmName("channelToSearchChannelUiState")
fun Channel.toUiState(): SearchChannelUiState =
    SearchChannelUiState(
        id = id,
        name = name,
    )
@JvmName("channelsToSearchChannelsUiState")
fun List<Channel>.toUiState(): List<SearchChannelUiState> = this.map { it.toUiState() }