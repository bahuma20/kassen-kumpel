package io.bahuma.kassenkumpel.feature_transactions.presentation.transactions.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import io.bahuma.kassenkumpel.feature_transactions.presentation.transactions.TransactionsViewModel
import java.util.Date

@Composable
fun TransactionsScreen(
    viewModel: TransactionsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    LazyColumn {
        items(state.transactions) {
            Text(
                text = it.transactionId.toString() + " - " + android.icu.text.DateFormat.getDateTimeInstance()
                    .format(Date.from(it.timestamp)) + " - " + it.amount.toString() + " - " + it.paymentMethod.name
            )
        }
    }
}