package com.scgdigital.news.presentation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.scgdigital.common.presentation.base.NavigationEvent
import com.scgdigital.common.presentation.component.SCGDigitalScaffold
import com.scgdigital.common.presentation.dialog.SCGDigitalDialogDataAction
import com.scgdigital.common.presentation.dialog.toDialogData
import com.scgdigital.resource.R
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@OptIn(
    ExperimentalMaterialApi::class,
)
@Composable
fun NewsContainer(
    isSessionTimeout: Boolean,
    data: String?,
    onNavigate: (NavigationEvent) -> Unit,
) {

    val viewModel: NewsViewModel = getViewModel {
        parametersOf(isSessionTimeout)
    }

    val state = viewModel.state.observeAsState(NewsViewState()).value
    val lifecycleOwner = LocalLifecycleOwner.current

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden, confirmStateChange = { true })

    DisposableEffect(Unit) {
        viewModel.event.observe(lifecycleOwner) { event ->
            when (event) {
                is NewsViewEvent.Navigate -> {
                    onNavigate(event)
                }
                is NewsViewEvent.ShowSnackbar -> coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(context.getString(event.message))
                }
                is NewsViewEvent.OpenBottomSheet -> {
                    coroutineScope.launch {
                        when {
                            event.isBottomSheetVisible -> {
                                sheetState.show()
                            }
                            else -> {
                                sheetState.hide()
                            }
                        }
                    }
                }
            }
        }

        viewModel.onEnterScreen()

        coroutineScope.launch {
            viewModel.getNews("*")
        }

        onDispose {}
    }

    SCGDigitalScaffold(
        scaffoldState = scaffoldState,
        dialogData = state.error?.toDialogData(
            fullScreenAction = SCGDigitalDialogDataAction(
                text = stringResource(R.string.global_button_refresh),
            )
        ),
        fullScreenLoading = state.isLoading,
        modifier = Modifier
            .navigationBarsWithImePadding()
    ) {
        NewsScreen(
            state,
            viewModel::performQuery,
            viewModel::onNewsClick,
            viewModel::convertDateTime
        )
    }
}
