package com.chocolate.viewmodel.home

import com.chocolate.viewmodel.base.BaseViewModel

sealed interface HomeUiEffect: BaseViewModel.BaseUiEffect{
    object NavigationToDrafts: HomeUiEffect
    object NavigationToStarred: HomeUiEffect
    object NavigationToSavedLater: HomeUiEffect
    data class NavigateToChannel(val id: Int, val name: String): HomeUiEffect
    object NavigateToOrganizationName: HomeUiEffect
    data class NavigateToTopic(val topicName: String): HomeUiEffect
    object NavigateToCreateChannel: HomeUiEffect
}
