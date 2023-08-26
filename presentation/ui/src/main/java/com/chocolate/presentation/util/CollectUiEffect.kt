package com.chocolate.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.chocolate.viewmodel.base.BaseViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest


@Composable
fun CollectUiEffect(
    effect: SharedFlow<BaseViewModel.BaseUiEffect>,
    effectHandler: (effect: BaseViewModel.BaseUiEffect) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        effect.collectLatest { effect ->
            effectHandler(effect)
        }
    }
}