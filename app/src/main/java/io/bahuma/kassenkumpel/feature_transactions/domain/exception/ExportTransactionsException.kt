package io.bahuma.kassenkumpel.feature_transactions.domain.exception

import java.lang.RuntimeException

class ExportTransactionsException(cause: Throwable) : RuntimeException(cause) {

}