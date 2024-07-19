package com.djordjekrutil.mozzartgreek.core.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel

// Edited Hilt's hiltViewModel() function which automatically searches for a
// viewModel scoped to an activity instead of looking for it in the backstack

@Composable
inline fun <reified VM : ViewModel> activityViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = LocalContext.current.findActivity() as ViewModelStoreOwner
): VM {
    return viewModel(viewModelStoreOwner, factory = null)
}

@Composable
inline fun <reified VM : ViewModel> delegateViewModelReady(
    onViewModelReady: @Composable (VM) -> Unit
) {
    val viewModel = mutableStateOf<VM?>(null)
    viewModel.value = activityViewModel()
    if (viewModel.value != null) {
        viewModel.value?.let {
            onViewModelReady.invoke(it)
        }
    }
}