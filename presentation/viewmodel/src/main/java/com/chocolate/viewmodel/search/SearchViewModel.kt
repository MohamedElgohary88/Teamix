package com.chocolate.viewmodel.search

import androidx.lifecycle.viewModelScope
import com.chocolate.entities.channel.Channel
import com.chocolate.entities.exceptions.NoConnectionException
import com.chocolate.entities.uills.Empty
import com.chocolate.usecases.channel.ManageChannelsUseCase
import com.chocolate.viewmodel.base.BaseViewModel
import com.chocolate.viewmodel.home.toChannelsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val manageChannels: ManageChannelsUseCase,
): BaseViewModel<SearchUiState,SearchEffect>(SearchUiState()),SearchInteraction {
    private var searchJob: Job? = null

    override fun onClickChannelItem(id: Int, name: String) {
        sendUiEffect(SearchEffect.NavigateToChannel(id,name))
    }

    override fun onChangeSearchQuery(query: String) {
        if(query.isEmpty()){
            _state.update { it.copy(isLoading = false, channelsUiState = emptyList(), query = query) }
            return
        }
        _state.update { it.copy(isLoading = true, query = query) }
        onSearch()
    }

    override fun onClickRetry() {
        _state.update { it.copy(isLoading = true) }
        onChangeSearchQuery(_state.value.query)
    }

    override fun onClickDeleteQuery() {
        _state.update { it.copy(query = String.Empty, channelsUiState = emptyList()) }
    }

    private fun onSearch() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(1000)
            onSearchChannels()
        }
    }

    private fun onSearchChannels() {
        tryToExecute(
            { manageChannels.searchChannels(_state.value.query) },
            ::onChangeSearchChannelsQuerySuccess,
            ::onChangeSearchQueryError
        )
    }

    private fun onChangeSearchChannelsQuerySuccess(channels: List<Channel>) {
        _state.update {
            it.copy(
                channelsUiState = channels.toChannelsUiState(),
                isLoading = false,
                showNoInternetLottie = false,
                error = null,
            )
        }
    }

    private fun onChangeSearchQueryError(throwable: Throwable) {
        val showNoInternetLottie = throwable is NoConnectionException
        _state.update {
            it.copy(
                isLoading = false,
                error = throwable.message,
                showNoInternetLottie = showNoInternetLottie
            )
        }
    }
}