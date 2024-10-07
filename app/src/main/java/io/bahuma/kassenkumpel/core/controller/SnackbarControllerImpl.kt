package io.bahuma.kassenkumpel.core.controller

import androidx.annotation.StringRes
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Immutable
private class SnackbarControllerImpl(
    private val snackbarHostState: SnackbarHostState,
    private val coroutineScope: CoroutineScope
) : SnackbarController {
    override fun showMessage(
        message: String,
        actionLabel: String?,
        withDismissAction: Boolean,
        duration: SnackbarDuration,
        onSnackbarResult: (SnackbarResult) -> Unit
    ) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                withDismissAction = withDismissAction,
                duration = duration
            ).let(onSnackbarResult)
        }
    }

    override fun showMessage(
        snackbarVisuals: SnackbarVisuals,
        onSnackbarResult: (SnackbarResult) -> Unit
    ) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(visuals = snackbarVisuals).let(onSnackbarResult)
        }
    }
}

@Stable
fun SnackbarController(
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
): SnackbarController = SnackbarControllerImpl(
    snackbarHostState = snackbarHostState,
    coroutineScope = coroutineScope,
)

@Composable
fun ProvideSnackbarController(
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalSnackbarController provides SnackbarController(
            snackbarHostState = snackbarHostState,
            coroutineScope = coroutineScope,
        ),
        content = content
    )
}

val LocalSnackbarController = staticCompositionLocalOf<SnackbarController> {
    error("No SnackbarController provided.")
}

sealed interface UserMessage {
    data class Text(val value: String) : UserMessage

    class StringResource(@StringRes val resId: Int, vararg val formatArgs: Any) : UserMessage

    companion object {
        fun from(value: String) = Text(value = value)

        fun from(@StringRes resId: Int, vararg formatArgs: Any) =
            StringResource(resId = resId, formatArgs = formatArgs)
    }
}

@Composable
fun UserMessage.asString() = when (this) {
    is UserMessage.Text -> value
    is UserMessage.StringResource -> stringResource(id = resId, formatArgs = formatArgs)
}

sealed interface SnackbarMessage {
    data class Text(
        val userMessage: UserMessage,
        val actionLabelMessage: UserMessage? = null,
        val withDismissAction: Boolean = false,
        val duration: SnackbarDuration = SnackbarDuration.Short,
        val onSnackbarResult: (SnackbarResult) -> Unit = {}
    ) : SnackbarMessage

    data class Visuals(
        val snackbarVisuals: SnackbarVisuals,
        val onSnackbarResult: (SnackbarResult) -> Unit = {}
    ) : SnackbarMessage

    companion object {
        fun from(
            userMessage: UserMessage,
            actionLabelMessage: UserMessage? = null,
            withDismissAction: Boolean = false,
            duration: SnackbarDuration = SnackbarDuration.Short,
            onSnackbarResult: (SnackbarResult) -> Unit = {}
        ) = Text(
            userMessage = userMessage,
            actionLabelMessage = actionLabelMessage,
            withDismissAction = withDismissAction,
            duration = duration,
            onSnackbarResult = onSnackbarResult
        )

        fun from(
            snackbarVisuals: SnackbarVisuals,
            onSnackbarResult: (SnackbarResult) -> Unit = {}
        ) = Visuals(snackbarVisuals = snackbarVisuals, onSnackbarResult = onSnackbarResult)
    }
}

@Composable
fun SnackbarMessageHandler(
    snackbarMessage: SnackbarMessage?,
    onDismissSnackbar: () -> Unit,
    snackbarController: SnackbarController = LocalSnackbarController.current,
) {
    if (snackbarMessage == null) return

    when (snackbarMessage) {
        is SnackbarMessage.Text -> {
            val message = snackbarMessage.userMessage.asString()
            val actionLabel = snackbarMessage.actionLabelMessage?.asString()

            LaunchedEffect(snackbarMessage, onDismissSnackbar) {
                snackbarController.showMessage(
                    message = message,
                    actionLabel = actionLabel,
                    withDismissAction = snackbarMessage.withDismissAction,
                    duration = snackbarMessage.duration,
                    onSnackbarResult = snackbarMessage.onSnackbarResult,
                )

                onDismissSnackbar()
            }
        }

        is SnackbarMessage.Visuals -> {
            LaunchedEffect(snackbarMessage, onDismissSnackbar) {
                snackbarController.showMessage(
                    snackbarVisuals = snackbarMessage.snackbarVisuals,
                    onSnackbarResult = snackbarMessage.onSnackbarResult
                )

                onDismissSnackbar()
            }
        }
    }
}